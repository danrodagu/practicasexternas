
package forms;

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
	@SafeHtml
	public String getUvus() {
		return this.uvus;
	}

	public void setUvus(final String uvus) {
		this.uvus = uvus;
	}	


}
