package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class RecoverPasswordForm {

	// Attributes -------------------------------------------------------------

	private String username;

	// Constructors -----------------------------------------------------------

	public RecoverPasswordForm() {
		super();
	}

	@NotNull
	@NotBlank
	@SafeHtml
	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	

	

}
