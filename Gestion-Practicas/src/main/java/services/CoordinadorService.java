//
//package services;
//
//import java.util.Collection;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.Assert;
//
//import domain.Coordinador;
//import repositories.CoordinadorRepository;
//import security.Authority;
//import security.LoginService;
//import security.UserAccount;
//import security.UserAccountService;
//
//@Service
//@Transactional
//public class CoordinadorService {
//
//	// Managed repository -----------------------------------------------------
//
//	@Autowired
//	private CoordinadorRepository coordinadorRepository;
//
//	// Supporting Services ----------------------------------------------------
//
//	@Autowired
//	private UserAccountService userAccountService;
//
//	// Constructors -----------------------------------------------------------
//	public CoordinadorService() {
//		super();
//	}
//
//	// Simple CRUD methods ----------------------------------------------------
//
//	public Coordinador create() {
//		Authority authority;
//		UserAccount userAccount;
//		Coordinador result;
//
//		authority = new Authority();
//		authority.setAuthority("COORDINADOR");
//
//		userAccount = this.userAccountService.create();
//
//		result = new Coordinador();
//
//		userAccount.addAuthority(authority);
//		result.setUserAccount(userAccount);
//
//		return result;
//
//	}
//
//	public Coordinador findOne(final int coordinadorId) {
//		Assert.isTrue(coordinadorId != 0);
//
//		Coordinador result;
//
//		result = this.coordinadorRepository.findOne(coordinadorId);
//
//		return result;
//	}
//
//	public Collection<Coordinador> findAll() {
//		Collection<Coordinador> result;
//
//		result = this.coordinadorRepository.findAll();
//
//		return result;
//	}
//
//	public Coordinador save(final Coordinador coordinador) {
//		Coordinador result;
//		BCryptPasswordEncoder encoder;
//
//		encoder = new BCryptPasswordEncoder();
//
//		coordinador.getUserAccount().setPassword(encoder.encode(coordinador.getUserAccount().getPassword()));
//
//		result = this.coordinadorRepository.save(coordinador);
//
//		return result;
//	}
//
//	public void delete(final Coordinador coordinador) {
//		this.coordinadorRepository.delete(coordinador);
//	}
//
//	// Other business methods -------------------------------------------------
//
//	public Coordinador findByUsername(final String username) {
//		Coordinador res;
//
//		res = this.coordinadorRepository.findByUsername(username);
//
//		return res;
//	}
//
//	public Coordinador findByPrincipal() {
//
//		Coordinador result;
//		UserAccount userAccount;
//
//		userAccount = LoginService.getPrincipal();
//		result = this.coordinadorRepository.findByPrincipal(userAccount.getId());
//
//		Assert.notNull(result);
//
//		return result;
//
//	}
//
//}
