
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
import forms.BusquedaTutoresForm;
import forms.TutorForm;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class TutorService {

	@PersistenceContext( unitName="Gestion-Practicas" )
	private EntityManager em;
	
	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository actorRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------
	public TutorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Actor create() {
		Authority authority;
		UserAccount userAccount;
		Actor result;

		authority = new Authority();
		authority.setAuthority("TUTOR");

		userAccount = this.userAccountService.create();

		result = new Actor();

		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);

		return result;

	}

	public Actor findOne(final int tutorId) {
		Assert.isTrue(tutorId != 0);

		Actor result;

		result = this.actorRepository.findOne(tutorId);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAllTutores();

		return result;
	}

	public Actor save(final Actor tutor) {
		Actor result;
		BCryptPasswordEncoder encoder;

		encoder = new BCryptPasswordEncoder();

		tutor.getUserAccount().setPassword(encoder.encode(tutor.getUserAccount().getPassword()));

		result = this.actorRepository.save(tutor);

		return result;
	}

	public void delete(final Actor tutor) {
		this.actorRepository.delete(tutor);
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
	
	public TutorForm takeForm(final Actor tutor) {
		TutorForm tutorForm;

		tutorForm = new TutorForm();

		tutorForm.setId(tutor.getId());
		tutorForm.setNombre(tutor.getNombre());
		tutorForm.setApellidos(tutor.getApellidos());
		tutorForm.setNif(tutor.getNif());
		tutorForm.setEmail(tutor.getEmail());
		tutorForm.setDepartamento(tutor.getDepartamento());

		// tutorForm.setPicture(tutor.getPicture());
		
		tutorForm.setUsername(tutor.getUserAccount().getUsername());

		return tutorForm;
	}

	public Actor reconstruct(final TutorForm tutorForm) {
		Actor res;

		if (tutorForm.getId() == 0) {
			res = this.create();
			
			//Generación de contraseña aleatoria
			String password = actorService.generateSecureRandomPassword();
			res.getUserAccount().setPassword(password);
			
			actorService.enviarCredencialesCorreo(tutorForm.getEmail(), tutorForm.getUsername(), password, false);
		} else {
			res = this.findByPrincipal();			
			
			// Comprobacion para que ambas contraseñas sean iguales
			Assert.isTrue(tutorForm.getPassword().equals(tutorForm.getPassword2()), "Las contraseñas no son iguales");
			
			Assert.isTrue(res.getId() == (tutorForm.getId()));
		}

		res.setNombre(tutorForm.getNombre());
		res.setApellidos(tutorForm.getApellidos());
		res.setNif(tutorForm.getNif());
		res.setEmail(tutorForm.getEmail());
		res.setDepartamento(tutorForm.getDepartamento());

		// res.setPicture(tutorForm.getPicture());
		
		res.getUserAccount().setUsername(tutorForm.getUsername());		

		return res;
	}
	
	public Collection<Actor> findMyStudents(){
		Collection<Actor> res;

		res = this.actorRepository.findMyStudents(actorService.findByPrincipal().getId());

		return res;
	}
	
	public List<Actor> tutoresFiltrados(final BusquedaTutoresForm busqForm){
		String query = "";
		
		query = "SELECT a from Actor a JOIN a.userAccount.authorities auth WHERE (auth.authority = 'TUTOR' OR auth.authority = 'COORDINADOR')";
		
		if(!StringUtils.isEmpty(busqForm.getNif())) {
			query += " AND a.nif LIKE '%" + busqForm.getNif() + "%'";
		}
		if(!StringUtils.isEmpty(busqForm.getNombre())) {
			query += " AND a.nombre LIKE '%" + busqForm.getNombre() + "%'";
		}
		if(!StringUtils.isEmpty(busqForm.getApellidos())) {
			query += " AND a.apellidos LIKE '%" + busqForm.getApellidos() + "%'";
		}
		if(!StringUtils.isEmpty(busqForm.getDepartamento())) {
			query += " AND a.departamento LIKE '%" + busqForm.getDepartamento() + "%'";
		}	
		
		TypedQuery<Actor> q = em.createQuery(query, Actor.class);
		
		return q.getResultList();
	}

}
