
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "`Alumno`", indexes = { @Index(columnList = "expedienteCerrado") })
public class Alumno extends Actor {

	// Attributes -------------------------------------------------------------

	private boolean expedienteCerrado;

	// Constructors -----------------------------------------------------------

	public Alumno() {
		super();
	}

	// Getters and setters ----------------------------------------------------

	@NotNull
	@NotBlank
	public boolean getExpedienteCerrado() {
		return this.expedienteCerrado;
	}

	public void setExpedienteCerrado(boolean expedienteCerrado) {
		this.expedienteCerrado = expedienteCerrado;
	}

	// Relationships ----------------------------------------------------------

	private Oferta ofertaAsignada;
	private Tutor tutorAsignado;

	@Valid
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	public Oferta getOfertaAsignada() {
		return this.ofertaAsignada;
	}

	public void setOfertaAsignada(Oferta ofertaAsignada) {
		this.ofertaAsignada = ofertaAsignada;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Tutor getTutorAsignado() {
		return this.tutorAsignado;
	}

	public void setTutorAsignado(Tutor tutorAsignado) {
		this.tutorAsignado = tutorAsignado;
	}

}
