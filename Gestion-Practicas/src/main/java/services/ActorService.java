
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository actorRepository;

	// Supporting Services ----------------------------------------------------

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

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();

		return result;
	}

	public Actor save(final Actor actor) {
		Actor result;

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

	public boolean checkRol(final String role) {
		boolean result;
		Collection<Authority> authorities;

		result = false;
		authorities = LoginService.getPrincipal().getAuthorities();
		for (final Authority a : authorities) {
			result = result || a.getAuthority().equals(role);
		}

		return result;
	}

	public boolean isAdministrativo() {
		boolean result;

		result = this.checkRol(Authority.ADMINISTRATIVO);

		return result;
	}

	public boolean isCoordinador() {
		boolean result;

		result = this.checkRol(Authority.COORDINADOR);

		return result;
	}

	public boolean isTutor() {
		boolean result;

		result = this.checkRol(Authority.TUTOR);

		return result;
	}

	public boolean isAlumno() {
		boolean result;

		result = this.checkRol(Authority.ALUMNO);

		return result;
	}

	public boolean isAnonymous() {
		boolean result;

		Authentication authentication;

		authentication = SecurityContextHolder.getContext().getAuthentication();
		result = authentication.getAuthorities().iterator().next().getAuthority().equals("ROLE_ANONYMOUS");

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

}
