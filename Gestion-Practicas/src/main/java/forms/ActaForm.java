package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class ActaForm {

	// Attributes -------------------------------------------------------------

	private String curso;
	private String convocatoria;
	
	private Integer idOferta;

	// Constructors -----------------------------------------------------------

	public ActaForm() {
		super();
	}

	@NotNull
	@NotBlank
	@SafeHtml
	public String getCurso() {
		return curso;
	}

	public void setCurso(final String curso) {
		this.curso = curso;
	}

	@NotNull
	@NotBlank
	@SafeHtml
	public String getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(final String convocatoria) {
		this.convocatoria = convocatoria;
	}

	public Integer getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(final Integer idOferta) {
		this.idOferta = idOferta;
	}

	

}
