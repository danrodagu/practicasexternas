
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class NuevoCoordiForm2 {

	private String uvus;
	private String confirmationToken;

	// Constructors ................
	public NuevoCoordiForm2() {
		super();
	}

	// Attributes .................
	
	@NotBlank
	@SafeHtml
	public String getUvus() {
		return this.uvus;
	}

	public void setUvus(final String uvus) {
		this.uvus = uvus;
	}

	@NotBlank
	@NotNull
	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(final String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

}
