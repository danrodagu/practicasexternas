
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import forms.AlumnoForm;
import repositories.ActorRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class AlumnoService {

	// Managed repository -----------------------------------------------------

	
	@Autowired
	private ActorRepository actorRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private OfertaService ofertaService;

//	@Autowired
//	private TutorService tutorService;
	
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
		result.setExpedienteCerrado(false);

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

//	public Actor save(final Actor alumno) {
//		Actor result;
//		BCryptPasswordEncoder encoder;
//
//		encoder = new BCryptPasswordEncoder();
//
//		alumno.getUserAccount().setPassword(encoder.encode(alumno.getUserAccount().getPassword()));
//
//		result = this.alumnoRepository.save(alumno);
//
//		return result;
//	}
//
//	public void delete(final Actor alumno) {
//		this.alumnoRepository.delete(alumno);
//	}
//
//	// Other business methods -------------------------------------------------
//
//	public Actor findByUsername(final String username) {
//		Actor res;
//
//		res = this.alumnoRepository.findByUsername(username);
//
//		return res;
//	}
//
//	public Actor findByPrincipal() {
//
//		Actor result;
//		UserAccount userAccount;
//
//		userAccount = LoginService.getPrincipal();
//		result = this.alumnoRepository.findByPrincipal(userAccount.getId());
//
//		Assert.notNull(result);
//
//		return result;
//
//	}

	public AlumnoForm takeForm(final Actor alumno) {
		AlumnoForm alumnoForm;

		alumnoForm = new AlumnoForm();

		alumnoForm.setId(alumno.getId());
		alumnoForm.setNombre(alumno.getNombre());
		alumnoForm.setApellidos(alumno.getApellidos());

		// alumnoForm.setPicture(alumno.getPicture());
		//
		alumnoForm.setUsername(alumno.getUserAccount().getUsername());

		return alumnoForm;
	}

//	public Actor reconstruct(final AlumnoForm alumnoForm) {
//		Actor res;
//
//		if (alumnoForm.getId() == 0) {
//			res = this.create();			
//		} else {
//			res = this.findByPrincipal();
//		}
//
//		// Comprobacion para que ambas contraseñas sean iguales
//		Assert.isTrue(alumnoForm.getPassword().equals(alumnoForm.getPassword2()), "Las contraseñas no son iguales");
//
//		if (alumnoForm.getId() != 0) {
//			Assert.isTrue(res.getId() == (alumnoForm.getId()));
//		}
//
//		
//		res.setNombre(alumnoForm.getNombre());
//		res.setApellidos(alumnoForm.getApellidos());
////		res.setOfertaAsignada(new Oferta());
////		res.setTutorAsignado(new Tutor());
//
//		// res.setPicture(alumnoForm.getPicture());
//		//
////		res.getUserAccount().setUsername(alumnoForm.getUsername());
////		res.getUserAccount().setPassword(alumnoForm.getPassword());
//
//		return res;
//	}
//	
//	public void registrarAlumno(final RegistroAlumnoForm registroAlumnoForm) {		
//		Actor alumno;
//		Oferta oferta;
//		Tutor tutor;
//		
////		// Comprobacion para que ambas contraseñas sean iguales
////		Assert.isTrue(registroAlumnoForm.getPassword().equals(registroAlumnoForm.getPassword2()), "Las contraseñas no son iguales");
//
//		tutor = tutorService.findOne(registroAlumnoForm.getIdTutor());
//		
//		oferta = new Oferta();
//		oferta.setTitulo(registroAlumnoForm.getTitulo());
//		oferta.setDescripcion(registroAlumnoForm.getDescripcion());
//		oferta.setDotacion(registroAlumnoForm.getDotacion());
//		oferta.setDuracion(registroAlumnoForm.getDuracion());
//		oferta.setEmpresa(registroAlumnoForm.getEmpresa());
//		oferta.setEsCurricular(registroAlumnoForm.getEsCurricular());
//		oferta.setLocalidad(registroAlumnoForm.getLocalidad());
//		oferta.setProvincia(registroAlumnoForm.getProvincia());
//		oferta.setPais(registroAlumnoForm.getPais());
//		
//		alumno = create();		
//		alumno.setNombre(registroAlumnoForm.getNombre());
//		alumno.setApellidos(registroAlumnoForm.getApellidos());		
////		alumno.setOfertaAsignada(oferta);
//		alumno.setTutorAsignado(tutor);
//
//		// res.setPicture(alumnoForm.getPicture());
//		//
//		alumno.getUserAccount().setUsername(registroAlumnoForm.getUsername());
//		alumno.getUserAccount().setPassword(registroAlumnoForm.getPassword());
//		
//		oferta = ofertaService.save(oferta);
//		alumno.setOfertaAsignada(oferta);
//		
//		alumno = save(alumno);
//		
//		this.carpetaService.carpetasPorDefecto(alumno);		
//
//	}

}
