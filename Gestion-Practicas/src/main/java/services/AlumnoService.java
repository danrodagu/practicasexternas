
package services;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import forms.AlumnoForm;
import forms.BusquedaAlumnosForm;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class AlumnoService {

	@PersistenceContext( unitName="Gestion-Practicas" )
	private EntityManager em;
	
	// Managed repository -----------------------------------------------------

	
	@Autowired
	private ActorRepository actorRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private OfertaService ofertaService;

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private CarpetaService carpetaService;
	
	// Constructors -----------------------------------------------------------
	public AlumnoService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Actor create() {
		Authority authority;
		UserAccount userAccount;
		Actor result;

		authority = new Authority();
		authority.setAuthority("ALUMNO");

		userAccount = this.userAccountService.create();

		result = new Actor();

		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);

		return result;

	}

	public Actor findOne(final int alumnoId) {
		Assert.isTrue(alumnoId != 0);

		Actor result;

		result = this.actorRepository.findOne(alumnoId);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAllAlumnos();

		return result;
	}

	public Actor save(final Actor alumno) {
		Actor result;
		BCryptPasswordEncoder encoder;

		encoder = new BCryptPasswordEncoder();

		alumno.getUserAccount().setPassword(encoder.encode(alumno.getUserAccount().getPassword()));

		result = this.actorRepository.save(alumno);

		return result;
	}

	public void delete(final Actor alumno) {
		this.actorRepository.delete(alumno);
	}

	// Other business methods -------------------------------------------------

	public Actor findByUsername(final String username) {
		Actor res;

		res = this.actorRepository.findByUsername(username);

		return res;
	}

	public Actor findByPrincipal() {

		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = this.actorRepository.findByPrincipal(userAccount.getId());

		Assert.notNull(result);

		return result;

	}
	

	public AlumnoForm takeForm(final Actor alumno) {
		AlumnoForm alumnoForm;

		alumnoForm = new AlumnoForm();

		alumnoForm.setId(alumno.getId());
		alumnoForm.setNombre(alumno.getNombre());
		alumnoForm.setApellidos(alumno.getApellidos());
		alumnoForm.setNif(alumno.getNif());
		alumnoForm.setEmail(alumno.getEmail());
		alumnoForm.setTitulacion(alumno.getTitulacion());

		// alumnoForm.setPicture(alumno.getPicture());
		//
		alumnoForm.setUsername(alumno.getUserAccount().getUsername());

		return alumnoForm;
	}

	public Actor reconstruct(final AlumnoForm alumnoForm) {
		Actor res;

		if (alumnoForm.getId() == 0) {
			res = this.create();			
		} else {
			res = this.findByPrincipal();
		}

		// Comprobacion para que ambas contraseñas sean iguales
		Assert.isTrue(alumnoForm.getPassword().equals(alumnoForm.getPassword2()), "Las contraseñas no son iguales");

		if (alumnoForm.getId() != 0) {
			Assert.isTrue(res.getId() == (alumnoForm.getId()));
		}

		
		res.setNombre(alumnoForm.getNombre());
		res.setApellidos(alumnoForm.getApellidos());
		res.setNif(alumnoForm.getNif());
		res.setEmail(alumnoForm.getEmail());
		res.setTitulacion(alumnoForm.getTitulacion());
//		res.setOfertaAsignada(new Oferta());
//		res.setTutorAsignado(new Actor());

		// res.setPicture(alumnoForm.getPicture());
		//
		res.getUserAccount().setUsername(alumnoForm.getUsername());
		res.getUserAccount().setPassword(alumnoForm.getPassword());
		
		

		return res;
	}
	
	public List<Actor> alumnosFiltrados(final BusquedaAlumnosForm busqForm, final Integer listAll){
		String query = "";
		Actor principal = actorService.findByPrincipal();
		
		query = "SELECT a from Actor a JOIN a.userAccount.authorities auth WHERE auth.authority = 'ALUMNO'";
		
		//Se filtran los alumnos por tutor asignado
		if(listAll != 1) {
			query += " AND a.id IN (SELECT o.alumnoAsignado.id FROM Oferta o WHERE o.tutorAsignado.id = " + principal.getId() + ")";
		}
		
		if(!StringUtils.isEmpty(busqForm.getNif())) {
			query += " AND a.nif LIKE '%" + busqForm.getNif() + "%'";
		}
		if(!StringUtils.isEmpty(busqForm.getNombre())) {
			query += " AND a.nombre LIKE '%" + busqForm.getNombre() + "%'";
		}
		if(!StringUtils.isEmpty(busqForm.getApellidos())) {
			query += " AND a.apellidos LIKE '%" + busqForm.getApellidos() + "%'";
		}
		if(!StringUtils.isEmpty(busqForm.getTitulacion())) {
			query += " AND a.titulacion LIKE '%" + busqForm.getTitulacion() + "%'";
		}
		
		if(busqForm.getTienePracticaAbierta() != null) {
			if(busqForm.getTienePracticaAbierta()) {
				query += " AND (SELECT COUNT(o1) FROM Oferta o1 WHERE o1.alumnoAsignado.id = a.id AND o1.expedienteCerrado = 0) >= 1";
			}else {
				query += " AND (SELECT COUNT(o1) FROM Oferta o1 WHERE o1.alumnoAsignado.id=a.id AND o1.expedienteCerrado = 0) = 0";
			}
		}		
		
		TypedQuery<Actor> q = em.createQuery(query, Actor.class);
		
		return q.getResultList();
	}

}
