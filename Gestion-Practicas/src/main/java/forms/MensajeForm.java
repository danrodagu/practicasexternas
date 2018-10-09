
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class MensajeForm {

	private String cuerpo;
	private String asunto;
	private int idReceptor;
//	private Collection<String> attachments;

	// Constructors ................
	public MensajeForm() {
		super();
	}

	// Attributes .................

	@NotBlank
	@SafeHtml
	public String getCuerpo() {
		return this.cuerpo;
	}

	public void setCuerpo(final String cuerpo) {
		this.cuerpo = cuerpo;
	}

	@NotBlank
	@SafeHtml
	public String getAsunto() {
		return this.asunto;
	}

	public void setAsunto(final String asunto) {
		this.asunto = asunto;
	}

	public int getIdReceptor() {
		return idReceptor;
	}

	public void setIdReceptor(final int idReceptor) {
		this.idReceptor = idReceptor;
	}

//	@ElementCollection
//	public Collection<String> getAttachments() {
//		return this.attachments;
//	}
//
//	public void setAttachments(final Collection<String> attachments) {
//		this.attachments = attachments;
//	}

}
