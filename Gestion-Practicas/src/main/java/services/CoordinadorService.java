
package services;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import forms.NuevoCoordiForm2;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class CoordinadorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository actorRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private CarpetaService carpetaService;

	// Constructors -----------------------------------------------------------
	public CoordinadorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Actor create() {
		Authority authority;
		UserAccount userAccount;
		Actor result;

		authority = new Authority();
		authority.setAuthority("COORDINADOR");

		userAccount = this.userAccountService.create();

		result = new Actor();

		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		result.getUserAccount().setEnabled(true);

		return result;

	}

	public Actor findOne(final int coordinadorId) {
		Assert.isTrue(coordinadorId != 0);

		Actor result;

		result = this.actorRepository.findOne(coordinadorId);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAllCoordinadores();

		return result;
	}

	public Actor save(final Actor coordinador) {
		Actor result;
		BCryptPasswordEncoder encoder;

		encoder = new BCryptPasswordEncoder();

		coordinador.getUserAccount().setPassword(encoder.encode(coordinador.getUserAccount().getPassword()));

		result = this.actorRepository.save(coordinador);

		return result;
	}

	public void delete(final Actor coordinador) {
		this.actorRepository.delete(coordinador);
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
	
	public void cambioCoordinadorUsuarioExistente(final int idFuturoCoordinador) {
		Collection<Actor> coordinadores;
		Actor coordinadorActual;
		Actor futuroCoordinador;
		Authority authCoordi = new Authority();
		Authority authTutor = new Authority();
		
		futuroCoordinador = actorService.findOne(idFuturoCoordinador);
		
		coordinadores = findAll();		
		Assert.isTrue(coordinadores.size() == 1);		
		coordinadorActual = coordinadores.iterator().next();
		
		//Se elimina el rol de tutor y se añade el de coordinador para el futuro coordinador
		for(Authority auth : futuroCoordinador.getUserAccount().getAuthorities()) {
			if(auth.getAuthority().equals("TUTOR")) {
				authTutor = auth;
				break;
			}
		}
		futuroCoordinador.getUserAccount().removeAuthority(authTutor);
		authCoordi = new Authority();
		authCoordi.setAuthority("COORDINADOR");
		futuroCoordinador.getUserAccount().addAuthority(authCoordi);
		
		//Se elimina el rol de coordinador y se añade el de tutor para el coordinador actual
		for(Authority auth : coordinadorActual.getUserAccount().getAuthorities()) {
			if(auth.getAuthority().equals("COORDINADOR")) {
				authCoordi = auth;
				break;
			}
		}
		coordinadorActual.getUserAccount().removeAuthority(authCoordi);
		authTutor = new Authority();
		authTutor.setAuthority("TUTOR");
		coordinadorActual.getUserAccount().addAuthority(authTutor);
		
		//Se guardan ambos actores
		actorService.save(coordinadorActual);
		actorService.save(futuroCoordinador);		
	}
	
	public void cambioCoordinadorUsuarioInexistente(final NuevoCoordiForm2 nuevoCoordiForm2, final HttpServletRequest request) {
		Collection<Actor> coordinadores;
		Actor coordinadorActual;
		Actor futuroCoordinador;
		Authority authTutor = new Authority();
		Authority authCoordi = new Authority();
		BCryptPasswordEncoder encoder;

		encoder = new BCryptPasswordEncoder();		
		
		coordinadores = findAll();	
		Assert.isTrue(coordinadores.size() == 1);		
		coordinadorActual = coordinadores.iterator().next();
		
		futuroCoordinador = create();
		
		//Generación de contraseña
		String password = actorService.generateSecureRandomPassword();			
//		futuroCoordinador.getUserAccount().setPassword(password);
		futuroCoordinador.getUserAccount().setPassword(encoder.encode(password));		
		
		futuroCoordinador.setNombre(nuevoCoordiForm2.getNombre());
		futuroCoordinador.setApellidos(nuevoCoordiForm2.getApellidos());
		futuroCoordinador.setNif(nuevoCoordiForm2.getNif());
		futuroCoordinador.setDepartamento(nuevoCoordiForm2.getDepartamento());
		futuroCoordinador.setEmail(nuevoCoordiForm2.getEmail());
		
		futuroCoordinador.getUserAccount().setUsername(nuevoCoordiForm2.getUsername());
		
		//Se elimina el rol de coordinador y se añade el de tutor para el coordinador actual
		for(Authority auth : coordinadorActual.getUserAccount().getAuthorities()) {
			if(auth.getAuthority().equals("COORDINADOR")) {
				authCoordi = auth;
				break;				
			}
		}
		coordinadorActual.getUserAccount().removeAuthority(authCoordi);
		authTutor = new Authority();
		authTutor.setAuthority("TUTOR");
		coordinadorActual.getUserAccount().addAuthority(authTutor);
		
		//Se guardan ambos actores
		coordinadorActual = actorService.save(coordinadorActual);
		futuroCoordinador = actorService.save(futuroCoordinador);	
		
		//Se crean las carpetas por defecto para el futuro coordinador
		carpetaService.carpetasPorDefecto(futuroCoordinador);
		
		//Se envían las credenciales de acceso para el futuro coordinador
		actorService.enviarCredencialesCorreo(nuevoCoordiForm2.getEmail(), nuevoCoordiForm2.getUsername(), password, false, request);
	}

}
