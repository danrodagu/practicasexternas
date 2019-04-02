
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class NoticiaForm {

	private int id;
	private String cuerpo;
	private String titulo;

	// Constructors ................
	public NoticiaForm() {
		super();
		this.id = 0;
	}

	// Attributes .................

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}
	
//	@NotBlank
	@SafeHtml
	public String getCuerpo() {
		return this.cuerpo;
	}

	public void setCuerpo(final String cuerpo) {
		this.cuerpo = cuerpo;
	}

	@NotBlank
	@SafeHtml
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

}
