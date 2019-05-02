
package forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class NuevoCoordiForm1 {

	private String uvus;

	// Constructors ................
	public NuevoCoordiForm1() {
		super();
	}

	// Attributes .................
	
	@NotBlank
	@NotNull
	@Size(min = 5, max = 32)
	@SafeHtml
	public String getUvus() {
		return this.uvus;
	}

	public void setUvus(final String uvus) {
		this.uvus = uvus;
	}	


}
