
package services;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Token;
import forms.EdicionPerfilForm;
import repositories.ActorRepository;
import repositories.TokenRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ActorService {
	
	@PersistenceContext( unitName="Gestion-Practicas" )
	private EntityManager em;

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository actorRepository;
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private TokenRepository tokenRepository;	
	

	// Supporting Services ----------------------------------------------------

	@Autowired
	private UtilService	utilService;
	
	// Constructors -----------------------------------------------------------
	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOne(actorId);

		return result;
	}
	
//	public Actor findOne(final int userAccountId) {
//		Actor result;
//		
//		result = this.actorRepository.findByPrincipal(userAccountId);
//
//		Assert.notNull(result);
//
//		return result;
//
//	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();

		return result;
	}
	
	public Collection<Actor> findAllActivos() {
		Collection<Actor> result;

		result = this.actorRepository.findAllActoresActivos();

		return result;
	}

	public Actor save(final Actor actor) {
		Actor result;
//		BCryptPasswordEncoder encoder;
//		
//		encoder = new BCryptPasswordEncoder();
//
//		actor.getUserAccount().setPassword(encoder.encode(actor.getUserAccount().getPassword()));

		result = this.actorRepository.save(actor);

		return result;
	}

	public void delete(final Actor actor) {
		this.actorRepository.delete(actor);
	}

	// Other business methods -------------------------------------------------

	public Actor findByUsername(final String username) {
		Actor res;

		res = this.actorRepository.findByUsername(username);

		return res;
	}

	public boolean checkRol(final String role, final int actorId) {
		boolean result;
		Collection<Authority> authorities;

		result = false;
//		authorities = LoginService.getPrincipal().getAuthorities();
		authorities = this.findOne(actorId).getUserAccount().getAuthorities();
		for (final Authority a : authorities) {
			result = result || a.getAuthority().equals(role);
		}

		return result;
	}

	public boolean isAdministrativo(final int actorId) {
		boolean result;

		result = this.checkRol(Authority.ADMINISTRATIVO, actorId);

		return result;
	}

	public boolean isCoordinador(final int actorId) {
		boolean result;

		result = this.checkRol(Authority.COORDINADOR, actorId);

		return result;
	}

	public boolean isTutor(final int actorId) {
		boolean result;

		result = this.checkRol(Authority.TUTOR, actorId);

		return result;
	}

	public boolean isAlumno(final int actorId) {
		boolean result;

		result = this.checkRol(Authority.ALUMNO, actorId);

		return result;
	}

	public boolean isAnonymousPrincipal() {
		boolean result;
		

		Authentication authentication;
		authentication = SecurityContextHolder.getContext().getAuthentication();
		result = authentication.getAuthorities().iterator().next().getAuthority().equals("ROLE_ANONYMOUS");
		
//		result = this.findOne(actorId).getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ROLE_ANONYMOUS");

		return result;
	}

	public Actor findByPrincipal() {

		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = this.actorRepository.findByPrincipal(userAccount.getId());

		Assert.notNull(result);

		return result;

	}
	
	public EdicionPerfilForm takeForm(final Actor actor) {
		EdicionPerfilForm edicionPerfilForm;

		edicionPerfilForm = new EdicionPerfilForm();

		edicionPerfilForm.setId(actor.getId());
		edicionPerfilForm.setNombre(actor.getNombre());
		edicionPerfilForm.setApellidos(actor.getApellidos());
		edicionPerfilForm.setNif(actor.getNif());
		edicionPerfilForm.setEmail(actor.getEmail());
		edicionPerfilForm.setTitulacion(actor.getTitulacion());
		edicionPerfilForm.setDepartamento(actor.getDepartamento());
		
		edicionPerfilForm.setUsername(actor.getUserAccount().getUsername());

		return edicionPerfilForm;
	}

	public Actor reconstruct(final EdicionPerfilForm edicionPerfilForm) {
		Actor actorPerfil;
		Actor actorLogueado;
		boolean mismoActorLogYPerfil;		
		
		actorPerfil = this.findOne(edicionPerfilForm.getId());
		actorLogueado = this.findByPrincipal();
		
		if(actorLogueado.getId() == actorPerfil.getId()) {
			mismoActorLogYPerfil = true;
		}else {
			mismoActorLogYPerfil = false;
		}		
		
		//El administrativo es el que puede modificar estos campos
		if(isAdministrativo(actorLogueado.getId())) {
			actorPerfil.setNombre(edicionPerfilForm.getNombre());
			actorPerfil.setApellidos(edicionPerfilForm.getApellidos());
			actorPerfil.setNif(edicionPerfilForm.getNif());
			
			actorPerfil.getUserAccount().setUsername(edicionPerfilForm.getUsername());
			
			if(isAlumno(actorPerfil.getId())){
				actorPerfil.setTitulacion(edicionPerfilForm.getTitulacion());
			}
			if(isTutor(actorPerfil.getId()) || isCoordinador(actorPerfil.getId())) {
				actorPerfil.setDepartamento(edicionPerfilForm.getDepartamento());
			}
		}
		
		actorPerfil.setEmail(edicionPerfilForm.getEmail());
		
		//Solo se puede modificar la contraseña si el usuario logueado es el mismo que el del perfil a cambiar. Si no se genera por el olvido de contraseña
		if(mismoActorLogYPerfil && !StringUtils.isBlank(edicionPerfilForm.getPassword()) && !StringUtils.isBlank(edicionPerfilForm.getPassword2())) {	
			// Comprobacion para que ambas contraseñas sean iguales
			Assert.isTrue(edicionPerfilForm.getPassword().equals(edicionPerfilForm.getPassword2()), "Las contraseñas no son iguales");
			
			actorPerfil.getUserAccount().setPassword(cifrarPassword(edicionPerfilForm.getPassword()));
		}		

		return actorPerfil;
	}
	
	public Stream<Character> getRandomSpecialChars(final int count) {
	    Random random = new SecureRandom();
	    IntStream specialChars = random.ints(count, 33, 45);
	    return specialChars.mapToObj(data -> (char) data);
	}
	
	public Stream<Character> getRandomNumbers(final int count) {
	    Random random = new SecureRandom();
	    IntStream numeros = random.ints(count, 48, 57);
	    return numeros.mapToObj(data -> (char) data);
	}
	
	public Stream<Character> getRandomAlphabets(final int count, final boolean mayus) {
	    Random random = new SecureRandom();
	    IntStream letras;
	    
	    if(mayus) {
	    	letras = random.ints(count, 65, 90);
	    }else {
	    	letras = random.ints(count, 97, 122);
	    }	    
	    
	    return letras.mapToObj(data -> (char) data);
	}
	
	public String generateSecureRandomPassword() {
	    Stream<Character> pwdStream = Stream.concat(getRandomNumbers(3), 
	      Stream.concat(getRandomSpecialChars(2), 
	      Stream.concat(getRandomAlphabets(4, true), getRandomAlphabets(4, false))));
	    List<Character> charList = pwdStream.collect(Collectors.toList());
	    Collections.shuffle(charList);
	    String password = charList.stream()
	        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
	        .toString();
	    
	    System.out.println(password);
	    
	    return password;
	}
	
	
	
	public void enviarCredencialesCorreo(final String email, final String username, final String password, final boolean recoverPassword, final HttpServletRequest request) {		
		String dominio = "";
		String subject;
		String message;
		
		dominio = utilService.getDominio(request);
		
		// informacion del correo
        String recipientAddress = email;
        
        if(!recoverPassword) {
        	subject = "Bienvenido a la Plataforma de Gestión de Prácticas Externas";
        	
        	 message = "Bienvenido a la Plataforma de Gestión de Prácticas Externas."
     				+ "<br /><br />"
     				+ "Sus credenciales de usuario son:"
     				+ "<br />"
     				+ "<b>Usuario</b>: " + username + "<br />"
             		+ "<b>Contraseña</b>: " + password
             		+ "<br /><br />"
             		+ "Puede acceder haciendo click " + "<a href='http://" + dominio + "/security/login.do' target='_blank'>aquí</a>";
        }else {
        	subject = "Nueva contraseña para la Plataforma de Gestión de Prácticas Externas";
        	
	    	 message = "Sus nuevas credenciales de usuario son:"
	 				+ "<br /><br />"
	 				+ "<b>Usuario</b>: " + username + "<br />"
	         		+ "<b>Contraseña</b>: " + password
	         		+ "<br /><br />"
	         		+ "Puede acceder haciendo click " + "<a href='http://" + dominio + "/security/login.do' target='_blank'>aquí</a>";
        }
        
            
	     
	    // debug
	    System.out.println("To: " + recipientAddress);
	    System.out.println("Subject: " + subject);
	    System.out.println("Message: " + message);
	    
	    // creacion del mensaje
	    MimeMessage mimeMessage = mailSender.createMimeMessage();
	    MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			helper.setSubject(subject);
			helper.setTo(recipientAddress);			
		    mimeMessage.setContent(message, "text/html");			    
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	     
	    // envia el email
		mailSender.send(mimeMessage);
	}
	
	public void enviarFormCambioCoordiCorreo(final String email, final Token token, final HttpServletRequest request) {	
		String dominio = "";
		String subject;
		String message;
		
		dominio = utilService.getDominio(request);
		
		// informacion del correo
        String recipientAddress = email;        
        
    	subject = "Cambio de coordinador de la Plataforma de Gestión de Prácticas Externas";
    	
    	message = "Si ha recibido este email es porque usted es el próximo coordinador de la Plataforma de Gestión de Prácticas Externas."
    			+ "<br /><br />"
    			+ "Para proceder a su activación como coordinador acceda al siguiente enlace en menos de 24 horas desde que reciba este mensaje: "
    			+ "<a href='http://" + dominio + "/Gestion-Practicas/coordinador/nuevoCoordinador.do?confirmationToken=" + token.getConfirmationToken() + "&fase=1' target='_blank'>aquí</a>";
               
	     
	    // debug
	    System.out.println("To: " + recipientAddress);
	    System.out.println("Subject: " + subject);
	    System.out.println("Message: " + message);
	    
	    // creacion del mensaje
	    MimeMessage mimeMessage = mailSender.createMimeMessage();
	    MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			helper.setSubject(subject);
			helper.setTo(recipientAddress);			
		    mimeMessage.setContent(message, "text/html");			    
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	     
	    // envia el email
		mailSender.send(mimeMessage);
		tokenRepository.save(token);
	}
	
	public String cifrarPassword(final String password) {
		String passCifrada;		
		BCryptPasswordEncoder encoder;
		
		encoder = new BCryptPasswordEncoder();

		passCifrada = encoder.encode(password);
		
		return passCifrada;
	}
	
	public Boolean existePassword(final String password) {
		Integer passwordsIguales;
		String passEncrypt;
		
		Assert.isTrue(!StringUtils.isEmpty(password));
		passEncrypt = cifrarPassword(password);

		passwordsIguales = this.actorRepository.passwordsIguales(passEncrypt);
		
		if(passwordsIguales >= 1) {
			return true;
		}else {
			return false;
		}
	}
	
	public void changePassword (final Actor actor, final String password) {		
		actor.getUserAccount().setPassword(password);
		
		save(actor);
	}
	
	public void desactivarUsuario(final int actorId) {
		Actor actor;
		
		actor = this.findOne(actorId);
		
		Assert.notNull(actor);		
		
		actor.getUserAccount().setEnabled(false);
	}
	
	public void activarUsuario(final int actorId) {
		Actor actor;
		
		actor = this.findOne(actorId);
		
		Assert.notNull(actor);		
		
		actor.getUserAccount().setEnabled(true);
	}
	
	

}
