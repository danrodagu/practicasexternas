
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class InvalidaEvaluacionForm {

	private String justificacion;
	private Integer idOferta;

	// Constructors ................
	public InvalidaEvaluacionForm() {
		super();
	}

	// Attributes .................
	
	@NotBlank
	@NotNull
	@SafeHtml
	public String getJustificacion() {
		return this.justificacion;
	}

	public void setJustificacion(final String justificacion) {
		this.justificacion = justificacion;
	}

	@NotNull
	public Integer getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(final Integer idOferta) {
		this.idOferta = idOferta;
	}	


}
