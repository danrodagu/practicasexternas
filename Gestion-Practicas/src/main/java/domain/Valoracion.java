
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Valoracion extends DomainEntity {

	private String titulo;
	private String cuerpo;
	private boolean esFinal;

	// Constructors -----------------------------------------------------------

	public Valoracion() {
		super();
	}

	// Getters and setters ----------------------------------------------------

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

	public boolean getEsFinal() {
		return this.esFinal;
	}

	public void setEsFinal(final boolean esFinal) {
		this.esFinal = esFinal;
	}

	// Relationships ----------------------------------------------------------

	private Actor alumno;

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Actor getAlumno() {
		return this.alumno;
	}

	public void setAlumno(final Actor alumno) {
		this.alumno = alumno;
	}

}
