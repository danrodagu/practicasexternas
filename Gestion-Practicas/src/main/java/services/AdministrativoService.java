
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrativo;
import forms.AdministrativoForm;
import repositories.AdministrativoRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class AdministrativoService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministrativoRepository administrativoRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;

	// Constructors -----------------------------------------------------------
	public AdministrativoService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Administrativo create() {
		Authority authority;
		UserAccount userAccount;
		Administrativo result;

		authority = new Authority();
		authority.setAuthority("ADMINISTRATIVO");

		userAccount = this.userAccountService.create();

		result = new Administrativo();

		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);

		return result;

	}

	public Administrativo findOne(final int administrativoId) {
		Assert.isTrue(administrativoId != 0);

		Administrativo result;

		result = this.administrativoRepository.findOne(administrativoId);

		return result;
	}

	public Collection<Administrativo> findAll() {
		Collection<Administrativo> result;

		result = this.administrativoRepository.findAll();

		return result;
	}

	public Administrativo save(final Administrativo administrativo) {
		Administrativo result;
		BCryptPasswordEncoder encoder;

		encoder = new BCryptPasswordEncoder();

		administrativo.getUserAccount().setPassword(encoder.encode(administrativo.getUserAccount().getPassword()));

		result = this.administrativoRepository.save(administrativo);

		return result;
	}

	public void delete(final Administrativo administrativo) {
		this.administrativoRepository.delete(administrativo);
	}

	// Other business methods -------------------------------------------------

	public Administrativo findByUsername(final String username) {
		Administrativo res;

		res = this.administrativoRepository.findByUsername(username);

		return res;
	}

	public Administrativo findByPrincipal() {

		Administrativo result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = this.administrativoRepository.findByPrincipal(userAccount.getId());

		Assert.notNull(result);

		return result;

	}
	
	public AdministrativoForm takeForm(final Administrativo administrativo) {
		AdministrativoForm administrativoForm;

		administrativoForm = new AdministrativoForm();

		administrativoForm.setId(administrativo.getId());
		administrativoForm.setNombre(administrativo.getNombre());
		administrativoForm.setApellidos(administrativo.getApellidos());

		// administrativoForm.setPicture(administrativo.getPicture());
		
		administrativoForm.setUsername(administrativo.getUserAccount().getUsername());

		return administrativoForm;
	}

	public Administrativo reconstruct(final AdministrativoForm administrativoForm) {
		Administrativo res;

		if (administrativoForm.getId() == 0) {
			res = this.create();
		} else {
			res = this.findByPrincipal();
		}

		// Comprobacion para que ambas contraseñas sean iguales
		Assert.isTrue(administrativoForm.getPassword().equals(administrativoForm.getPassword2()), "Las contraseñas no son iguales");

		if (administrativoForm.getId() != 0) {
			Assert.isTrue(res.getId() == (administrativoForm.getId()));
		}

		res.setNombre(administrativoForm.getNombre());
		res.setApellidos(administrativoForm.getApellidos());	

		// res.setPicture(administrativoForm.getPicture());
		
		res.getUserAccount().setUsername(administrativoForm.getUsername());
		res.getUserAccount().setPassword(administrativoForm.getPassword());

		return res;
	}

}
