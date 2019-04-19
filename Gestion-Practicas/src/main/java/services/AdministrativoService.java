
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import forms.AdministrativoForm;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class AdministrativoService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository actorRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------
	public AdministrativoService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Actor create() {
		Authority authority;
		UserAccount userAccount;
		Actor result;

		authority = new Authority();
		authority.setAuthority("ADMINISTRATIVO");

		userAccount = this.userAccountService.create();

		result = new Actor();

		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		result.setActivo(true);

		return result;

	}

	public Actor findOne(final int administrativoId) {
		Assert.isTrue(administrativoId != 0);

		Actor result;

		result = this.actorRepository.findOne(administrativoId);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAllAdministrativos();

		return result;
	}
	
	public Collection<Actor> findAllActivos() {
		Collection<Actor> result;

		result = this.actorRepository.findAllAdministrativosActivos();

		return result;
	}

	public Actor save(final Actor administrativo) {
		Actor result;
		BCryptPasswordEncoder encoder;

		encoder = new BCryptPasswordEncoder();

		administrativo.getUserAccount().setPassword(encoder.encode(administrativo.getUserAccount().getPassword()));

		result = this.actorRepository.save(administrativo);

		return result;
	}

	public void delete(final Actor administrativo) {
		this.actorRepository.delete(administrativo);
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
	
	public AdministrativoForm takeForm(final Actor administrativo) {
		AdministrativoForm administrativoForm;

		administrativoForm = new AdministrativoForm();

		administrativoForm.setId(administrativo.getId());
		administrativoForm.setNombre(administrativo.getNombre());
		administrativoForm.setApellidos(administrativo.getApellidos());
		administrativoForm.setNif(administrativo.getNif());
		administrativoForm.setEmail(administrativo.getEmail());

		// administrativoForm.setPicture(administrativo.getPicture());
		
		administrativoForm.setUsername(administrativo.getUserAccount().getUsername());

		return administrativoForm;
	}

	public Actor reconstruct(final AdministrativoForm administrativoForm) {
		Actor res;

		if (administrativoForm.getId() == 0) {
			res = this.create();
			
			//Generación de contraseña
			String password = actorService.generateSecureRandomPassword();			
			res.getUserAccount().setPassword(password);
			
			actorService.enviarCredencialesCorreo(administrativoForm.getEmail(), administrativoForm.getUsername(), password, false);
		} else {
			res = this.findByPrincipal();
			
			// Comprobacion para que ambas contraseñas sean iguales
			Assert.isTrue(administrativoForm.getPassword().equals(administrativoForm.getPassword2()), "Las contraseñas no son iguales");
			
			Assert.isTrue(res.getId() == (administrativoForm.getId()));
		}

		res.setNombre(administrativoForm.getNombre());
		res.setApellidos(administrativoForm.getApellidos());
		res.setNif(administrativoForm.getNif());
		res.setEmail(administrativoForm.getEmail());

		// res.setPicture(administrativoForm.getPicture());
		
		res.getUserAccount().setUsername(administrativoForm.getUsername());


		return res;
	}

}
