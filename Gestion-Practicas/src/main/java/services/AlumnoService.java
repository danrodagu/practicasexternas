
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Alumno;
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

}
