/*
 * UserAccountService.java

 */

package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class UserAccountService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserAccountRepository userAccountRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public UserAccountService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public UserAccount create() {

		UserAccount result = new UserAccount();

		return result;
	}

	public void save(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		this.userAccountRepository.save(userAccount);
	}

	// Other business methods -------------------------------------------------

}
