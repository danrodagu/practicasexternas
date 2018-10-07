
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import domain.Actor;

public class MensajeForm {

	private String cuerpo;
	private String asunto;
	private Actor receptor;
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

	@NotNull
	public Actor getReceptor() {
		return this.receptor;
	}

	public void setReceptor(final Actor receptor) {
		this.receptor = receptor;
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
