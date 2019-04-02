
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
public class Noticia extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private Date fechaModificacion;
	private String titulo;
	private String cuerpo;

	// Constructors -----------------------------------------------------------

	public Noticia() {
		super();
	}

	// Getters and setters ----------------------------------------------------

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(final Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@NotNull
	@NotBlank
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	@NotNull
	@NotBlank
	public String getCuerpo() {
		return this.cuerpo;
	}

	public void setCuerpo(final String cuerpo) {
		this.cuerpo = cuerpo;
	}

	// Relationships ------------------------------------------------------------
	private Actor autor;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getAutor() {
		return this.autor;
	}

	public void setAutor(final Actor autor) {
		this.autor = autor;
	}

	

}
