
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
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
		result.setActivo(true);

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

}
