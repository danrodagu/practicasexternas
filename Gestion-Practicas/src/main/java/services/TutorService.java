
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Tutor;
import repositories.TutorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class TutorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TutorRepository tutorRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;

	// Constructors -----------------------------------------------------------
	public TutorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Tutor create() {
		Authority authority;
		UserAccount userAccount;
		Tutor result;

		authority = new Authority();
		authority.setAuthority("TUTOR");

		userAccount = this.userAccountService.create();

		result = new Tutor();

		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);

		return result;

	}

	public Tutor findOne(final int tutorId) {
		Assert.isTrue(tutorId != 0);

		Tutor result;

		result = this.tutorRepository.findOne(tutorId);

		return result;
	}

	public Collection<Tutor> findAll() {
		Collection<Tutor> result;

		result = this.tutorRepository.findAll();

		return result;
	}

	public Tutor save(final Tutor tutor) {
		Tutor result;
		BCryptPasswordEncoder encoder;

		encoder = new BCryptPasswordEncoder();

		tutor.getUserAccount().setPassword(encoder.encode(tutor.getUserAccount().getPassword()));

		result = this.tutorRepository.save(tutor);

		return result;
	}

	public void delete(final Tutor tutor) {
		this.tutorRepository.delete(tutor);
	}

	// Other business methods -------------------------------------------------

	public Tutor findByUsername(final String username) {
		Tutor res;

		res = this.tutorRepository.findByUsername(username);

		return res;
	}

	public Tutor findByPrincipal() {

		Tutor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = this.tutorRepository.findByPrincipal(userAccount.getId());

		Assert.notNull(result);

		return result;

	}

}
