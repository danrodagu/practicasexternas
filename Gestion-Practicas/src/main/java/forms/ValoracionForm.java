
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class ValoracionForm {

	// Attributes -------------------------------------------------------------

	private int id;
	private String texto;
	private Integer notaCurricular;
	private String notaExtracurricular;
	
	private Integer idOferta; 

	// Constructors -----------------------------------------------------------

	public ValoracionForm() {
		super();
		this.id = 0;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotNull
	@NotBlank
	@SafeHtml
	public String getTexto() {
		return texto;
	}

	public void setTexto(final String texto) {
		this.texto = texto;
	}

	public Integer getNotaCurricular() {
		return notaCurricular;
	}

	public void setNotaCurricular(final Integer notaCurricular) {
		this.notaCurricular = notaCurricular;
	}

	public String getNotaExtracurricular() {
		return notaExtracurricular;
	}

	public void setNotaExtracurricular(final String notaExtracurricular) {
		this.notaExtracurricular = notaExtracurricular;
	}

	public Integer getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(final Integer idOferta) {
		this.idOferta = idOferta;
	}

	

}
