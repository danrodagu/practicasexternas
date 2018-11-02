
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Mensaje extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private Date fecha;
	private String asunto;
	private String cuerpo;
	// private Collection<String> attachments;

	// Constructors -----------------------------------------------------------

	public Mensaje() {
		super();
	}

	// Getters and setters ----------------------------------------------------

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	@NotNull
	@NotBlank
	public String getAsunto() {
		return this.asunto;
	}

	public void setAsunto(final String asunto) {
		this.asunto = asunto;
	}

	@NotNull
	@NotBlank
	public String getCuerpo() {
		return this.cuerpo;
	}

	public void setCuerpo(final String cuerpo) {
		this.cuerpo = cuerpo;
	}

	// @ElementCollection
	// public Collection<String> getAttachments() {
	// return this.attachments;
	// }
	//
	// public void setAttachments(final Collection<String> attachments) {
	// this.attachments = attachments;
	// }

	// Relationships ------------------------------------------------------------
	private Actor emisor;
	private Actor receptor;
	private Carpeta carpeta;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getEmisor() {
		return this.emisor;
	}

	public void setEmisor(final Actor emisor) {
		this.emisor = emisor;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getReceptor() {
		return this.receptor;
	}

	public void setReceptor(final Actor receptor) {
		this.receptor = receptor;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Carpeta getCarpeta() {
		return this.carpeta;
	}

	public void setCarpeta(final Carpeta carpeta) {
		this.carpeta = carpeta;
	}

}
