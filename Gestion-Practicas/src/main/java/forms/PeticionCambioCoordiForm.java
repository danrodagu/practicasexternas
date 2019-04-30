
package forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class PeticionCambioCoordiForm {

	private String email;

	// Constructors ................
	public PeticionCambioCoordiForm() {
		super();
	}

	// Attributes .................
	
	@NotBlank
	@Email
	@SafeHtml
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

}
