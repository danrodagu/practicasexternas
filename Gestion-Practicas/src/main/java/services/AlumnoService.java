
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Alumno;
import domain.Oferta;
import domain.Tutor;
import forms.AlumnoForm;
import forms.RegistroAlumnoForm;
import repositories.AlumnoRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class AlumnoService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AlumnoRepository alumnoRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private OfertaService ofertaService;

	@Autowired
	private TutorService tutorService;
	
	// Constructors -----------------------------------------------------------
	public AlumnoService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Alumno create() {
		Authority authority;
		UserAccount userAccount;
		Alumno result;

		authority = new Authority();
		authority.setAuthority("ALUMNO");

		userAccount = this.userAccountService.create();

		result = new Alumno();

		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		result.setExpedienteCerrado(false);

		return result;

	}

	public Alumno findOne(final int alumnoId) {
		Assert.isTrue(alumnoId != 0);

		Alumno result;

		result = this.alumnoRepository.findOne(alumnoId);

		return result;
	}

	public Collection<Alumno> findAll() {
		Collection<Alumno> result;

		result = this.alumnoRepository.findAll();

		return result;
	}

	public Alumno save(final Alumno alumno) {
		Alumno result;
		BCryptPasswordEncoder encoder;

		encoder = new BCryptPasswordEncoder();

		alumno.getUserAccount().setPassword(encoder.encode(alumno.getUserAccount().getPassword()));

		result = this.alumnoRepository.save(alumno);

		return result;
	}

	public void delete(final Alumno alumno) {
		this.alumnoRepository.delete(alumno);
	}

	// Other business methods -------------------------------------------------

	public Alumno findByUsername(final String username) {
		Alumno res;

		res = this.alumnoRepository.findByUsername(username);

		return res;
	}

	public Alumno findByPrincipal() {

		Alumno result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = this.alumnoRepository.findByPrincipal(userAccount.getId());

		Assert.notNull(result);

		return result;

	}

	public AlumnoForm takeForm(final Alumno alumno) {
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

	public Alumno reconstruct(final AlumnoForm alumnoForm) {
		Alumno res;

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
//		res.setOfertaAsignada(new Oferta());
//		res.setTutorAsignado(new Tutor());

		// res.setPicture(alumnoForm.getPicture());
		//
//		res.getUserAccount().setUsername(alumnoForm.getUsername());
//		res.getUserAccount().setPassword(alumnoForm.getPassword());

		return res;
	}
	
	public void registrarAlumno(final RegistroAlumnoForm registroAlumnoForm) {		
		Alumno alumno;
		Oferta oferta;
		Tutor tutor;
		
//		// Comprobacion para que ambas contraseñas sean iguales
//		Assert.isTrue(registroAlumnoForm.getPassword().equals(registroAlumnoForm.getPassword2()), "Las contraseñas no son iguales");

		tutor = tutorService.findOne(registroAlumnoForm.getIdTutor());
		
		oferta = new Oferta();
		oferta.setTitulo(registroAlumnoForm.getTitulo());
		oferta.setDescripcion(registroAlumnoForm.getDescripcion());
		oferta.setDotacion(registroAlumnoForm.getDotacion());
		oferta.setDuracion(registroAlumnoForm.getDuracion());
		oferta.setEmpresa(registroAlumnoForm.getEmpresa());
		oferta.setEsCurricular(registroAlumnoForm.getEsCurricular());
		oferta.setLocalidad(registroAlumnoForm.getLocalidad());
		oferta.setProvincia(registroAlumnoForm.getProvincia());
		oferta.setPais(registroAlumnoForm.getPais());
		
		alumno = create();		
		alumno.setNombre(registroAlumnoForm.getNombre());
		alumno.setApellidos(registroAlumnoForm.getApellidos());		
		alumno.setOfertaAsignada(oferta);
		alumno.setTutorAsignado(tutor);

		// res.setPicture(alumnoForm.getPicture());
		//
		alumno.getUserAccount().setUsername(registroAlumnoForm.getUsername());
		alumno.getUserAccount().setPassword(registroAlumnoForm.getPassword());
		
		oferta = ofertaService.save(oferta);
		alumno = save(alumno);				

	}

}
