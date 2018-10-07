package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class CarpetaForm {

	// Attributes -------------------------------------------------------------

	private Integer id;
	private String nombre;

	// Constructors -----------------------------------------------------------

	public CarpetaForm() {
		super();
		this.id = 0;
	}

	@NotBlank
	@NotNull
	@SafeHtml
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	@NotNull
	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

}
