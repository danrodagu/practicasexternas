
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
		result.getUserAccount().setEnabled(true);

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
	
	public Collection<Actor> findAllActivos() {
		Collection<Actor> result;

		result = this.actorRepository.findAllAlumnosActivos();

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
			
			//Generación de contraseña
			String password = actorService.generateSecureRandomPassword();
			res.getUserAccount().setPassword(password);
			
			actorService.enviarCredencialesCorreo(alumnoForm.getEmail(), alumnoForm.getUsername(), password, false);
		} else {
			res = this.findByPrincipal();
			
			// Comprobacion para que ambas contraseñas sean iguales
			Assert.isTrue(alumnoForm.getPassword().equals(alumnoForm.getPassword2()), "Las contraseñas no son iguales");
			
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
		

		return res;
	}
	
//	public List<Actor> alumnosFiltrados(final BusquedaAlumnosForm busqForm, final Integer listAll){
//		String query = "";
//		Actor principal = actorService.findByPrincipal();
//		
//		query = "SELECT a from Actor a JOIN a.userAccount.authorities auth WHERE auth.authority = 'ALUMNO'";
//		
//		//Se filtran los alumnos por tutor asignado
//		if(listAll != 1) {
//			query += " AND a.id IN (SELECT o.alumnoAsignado.id FROM Oferta o WHERE o.tutorAsignado.id = " + principal.getId() + ")";
//		}
//		
//		if(!StringUtils.isEmpty(busqForm.getNif())) {
//			query += " AND a.nif LIKE '%" + busqForm.getNif() + "%'";
//		}
//		if(!StringUtils.isEmpty(busqForm.getNombre())) {
//			query += " AND a.nombre LIKE '%" + busqForm.getNombre() + "%'";
//		}
//		if(!StringUtils.isEmpty(busqForm.getApellidos())) {
//			query += " AND a.apellidos LIKE '%" + busqForm.getApellidos() + "%'";
//		}
//		if(!StringUtils.isEmpty(busqForm.getTitulacion())) {
//			query += " AND a.titulacion LIKE '%" + busqForm.getTitulacion() + "%'";
//		}
//		
//		if(busqForm.getActivo() != null) {
//			if(busqForm.getActivo()) {
//				query += " AND a.userAccount.enabled = 1";
//			}else {
//				query += " AND a.userAccount.enabled = 0";
//			}
//		}
//		
//		if(busqForm.getTienePracticaAbierta() != null) {
//			if(busqForm.getTienePracticaAbierta()) {
//				query += " AND (SELECT COUNT(o1) FROM Oferta o1 WHERE o1.alumnoAsignado.id = a.id AND o1.expedienteCerrado = 0) >= 1";
//			}else {
//				query += " AND (SELECT COUNT(o1) FROM Oferta o1 WHERE o1.alumnoAsignado.id=a.id AND o1.expedienteCerrado = 0) = 0";
//			}
//		}
//		
//		if(busqForm.getTienePracticaAbierta() == null || busqForm.getTienePracticaAbierta()) {
//			if(!StringUtils.isEmpty(busqForm.getEmpresa())){
//				query += " AND a.id IN (SELECT o2.alumnoAsignado.id FROM Oferta o2 WHERE o2.empresa LIKE '%" + busqForm.getEmpresa() + "%')";
//			}
//			if(!StringUtils.isEmpty(busqForm.getCifEmp())) {
//				query += " AND a.id IN (SELECT o3.alumnoAsignado.id FROM Oferta o3 WHERE o3.cifEmp LIKE '%" + busqForm.getCifEmp() + "%')";
//			}
//			if(busqForm.getEsCurricular() != null) {
//				if(busqForm.getEsCurricular()) {
//					query += " AND a.id IN (SELECT o4.alumnoAsignado.id FROM Oferta o4 WHERE o4.esCurricular = 1)";
//				}else {
//					query += " AND a.id IN (SELECT o4.alumnoAsignado.id FROM Oferta o4 WHERE o4.esCurricular = 0)";
//				}
//			}
//			if(busqForm.getDuracion() != null) {
//				query += " AND a.id IN (SELECT o5.alumnoAsignado.id FROM Oferta o5 WHERE o5.duracion = " + busqForm.getDuracion() + ")";
//			}
//			if(busqForm.getHoras() != null) {
//				query += " AND a.id IN (SELECT o6.alumnoAsignado.id FROM Oferta o6 WHERE o6.horas = " + busqForm.getHoras() +")";
//			}
//			if(busqForm.getDotacion() != null) {
//				query += " AND a.id IN (SELECT o7.alumnoAsignado.id FROM Oferta o7 WHERE o7.dotacion = " + busqForm.getDotacion() + ")";
//			}
//			
//			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
//            String fechaDesde; 
//            String fechaHasta;
//			if(busqForm.getFechaInicioDesde() != null || busqForm.getFechaInicioHasta() != null) {				
//				if(busqForm.getFechaInicioHasta() == null) {
//					fechaDesde = dateFormat.format(busqForm.getFechaInicioDesde());  
//					query += " AND a.id IN (SELECT o8.alumnoAsignado.id FROM Oferta o8 WHERE o8.fechaInicio >= '" + fechaDesde + "')";
//				}else if (busqForm.getFechaInicioDesde() == null) {
//					fechaHasta = dateFormat.format(busqForm.getFechaInicioHasta());  
//					query += " AND a.id IN (SELECT o8.alumnoAsignado.id FROM Oferta o8 WHERE o8.fechaInicio <= '" + fechaHasta + "')";
//				}else {
//					fechaDesde = dateFormat.format(busqForm.getFechaInicioDesde());
//					fechaHasta = dateFormat.format(busqForm.getFechaInicioHasta());
//					query += " AND a.id IN (SELECT o8.alumnoAsignado.id FROM Oferta o8 WHERE o8.fechaInicio >= '" + fechaDesde + "'"
//							+ " AND o8.fechaInicio <= '" + fechaHasta + "')";
//				}
//			}
//			if(busqForm.getFechaFinDesde() != null || busqForm.getFechaFinHasta() != null) {
//				if(busqForm.getFechaFinHasta() == null) {
//					fechaDesde = dateFormat.format(busqForm.getFechaFinDesde());  
//					query += " AND a.id IN (SELECT o9.alumnoAsignado.id FROM Oferta o9 WHERE o9.fechaFin >= '" + fechaDesde + "')";
//				}else if (busqForm.getFechaFinDesde() == null) {
//					fechaHasta = dateFormat.format(busqForm.getFechaFinHasta());  
//					query += " AND a.id IN (SELECT o9.alumnoAsignado.id FROM Oferta o9 WHERE o9.fechaFin <= '" + fechaHasta + "')";
//				}else {
//					fechaDesde = dateFormat.format(busqForm.getFechaFinDesde());
//					fechaHasta = dateFormat.format(busqForm.getFechaFinHasta());
//					query += " AND a.id IN (SELECT o9.alumnoAsignado.id FROM Oferta o9 WHERE o9.fechaFin >= '" + fechaDesde + "'"
//							+ " AND o9.fechaInicio <= '" + fechaHasta + "')";
//				}
//			}			
//			
//			
//		}
//		
//	
//		
//		TypedQuery<Actor> q = em.createQuery(query, Actor.class);
//		
//		return q.getResultList();
//	}
	
	public List<Actor> alumnosFiltrados(final BusquedaAlumnosForm busqForm, final Integer listAll){
		String query = "";
		Actor principal = actorService.findByPrincipal();
		
		query = "SELECT a FROM Actor a, Oferta o JOIN a.userAccount.authorities auth WHERE auth.authority = 'ALUMNO' AND a.id = o.alumnoAsignado.id";
		
		//Se filtran los alumnos por tutor asignado
		if(listAll != 1) {
			query += " AND o.tutorAsignado.id = " + principal.getId();
		}
		
		if(busqForm != null) {
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
			
			if(busqForm.getActivo() != null) {
				if(busqForm.getActivo()) {
					query += " AND a.userAccount.enabled = 1";
				}else {
					query += " AND a.userAccount.enabled = 0";
				}
			}
			
			if(busqForm.getTienePracticaAbierta() != null) {
				if(busqForm.getTienePracticaAbierta()) {
					query += " AND (SELECT COUNT(o1) FROM Oferta o1 WHERE o1.id = o.id AND o1.alumnoAsignado.id = a.id AND o1.expedienteCerrado = 0) >= 1";
				}else {
					query += " AND (SELECT COUNT(o1) FROM Oferta o1 WHERE o1.id = o.id AND o1.alumnoAsignado.id = a.id AND o1.expedienteCerrado = 0) = 0";
				}
			}
			
			if(busqForm.getTienePracticaAbierta() == null || busqForm.getTienePracticaAbierta()) {
				if(!StringUtils.isEmpty(busqForm.getEmpresa())){
					query += " AND o.empresa LIKE '%" + busqForm.getEmpresa() + "%'";
				}
				if(!StringUtils.isEmpty(busqForm.getCifEmp())) {
					query += " AND o.cifEmp LIKE '%" + busqForm.getCifEmp() + "%'";
				}
				if(busqForm.getEsCurricular() != null) {
					if(busqForm.getEsCurricular()) {
						query += " AND o.esCurricular = 1";
					}else {
						query += " AND o.esCurricular = 0";
					}
				}
				if(!StringUtils.isEmpty(busqForm.getDuracion())) {
					String duracion = busqForm.getDuracion().replaceAll(",", "\\.");
					query += " AND o.duracion = " + duracion;
				}
				if(!StringUtils.isEmpty(busqForm.getHoras())) {
					query += " AND o.horas = " + busqForm.getHoras();
				}
				if(!StringUtils.isEmpty(busqForm.getDotacion())) {
					String dotacion = busqForm.getDotacion().replaceAll(",", "\\.");
					query += " AND o.dotacion = " + dotacion;
				}
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	            String fechaDesde; 
	            String fechaHasta;
				if(busqForm.getFechaInicioDesde() != null || busqForm.getFechaInicioHasta() != null) {				
					if(busqForm.getFechaInicioHasta() == null) {
						fechaDesde = dateFormat.format(busqForm.getFechaInicioDesde());  
						query += " AND o.fechaInicio >= '" + fechaDesde + "'";
					}else if (busqForm.getFechaInicioDesde() == null) {
						fechaHasta = dateFormat.format(busqForm.getFechaInicioHasta());  
						query += " AND o.fechaInicio <= '" + fechaHasta + "'";
					}else {
						fechaDesde = dateFormat.format(busqForm.getFechaInicioDesde());
						fechaHasta = dateFormat.format(busqForm.getFechaInicioHasta());
						query += " AND o.fechaInicio >= '" + fechaDesde + "'"
								+ " AND o.fechaInicio <= '" + fechaHasta + "'";
					}
				}
				if(busqForm.getFechaFinDesde() != null || busqForm.getFechaFinHasta() != null) {
					if(busqForm.getFechaFinHasta() == null) {
						fechaDesde = dateFormat.format(busqForm.getFechaFinDesde());  
						query += " AND o.fechaFin >= '" + fechaDesde + "'";
					}else if (busqForm.getFechaFinDesde() == null) {
						fechaHasta = dateFormat.format(busqForm.getFechaFinHasta());  
						query += " AND o.fechaFin <= '" + fechaHasta + "'";
					}else {
						fechaDesde = dateFormat.format(busqForm.getFechaFinDesde());
						fechaHasta = dateFormat.format(busqForm.getFechaFinHasta());
						query += " AND o.fechaFin >= '" + fechaDesde + "'"
								+ " AND o.fechaInicio <= '" + fechaHasta + "'";
					}
				}				
				
			}
		}		
		
		
		query += " GROUP BY a";		
	
		
		TypedQuery<Actor> q = em.createQuery(query, Actor.class);
		
		return q.getResultList();
	}

}
