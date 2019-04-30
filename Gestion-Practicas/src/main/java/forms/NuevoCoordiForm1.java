
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class NuevoCoordiForm1 {

	private String uvus;
	private String confirmationToken;

	// Constructors ................
	public NuevoCoordiForm1() {
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

	@NotNull
	@NotBlank
	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(final String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	


}
