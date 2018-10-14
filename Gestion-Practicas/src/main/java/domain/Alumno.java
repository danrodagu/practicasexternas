
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Access(AccessType.PROPERTY)
@Table(name = "`Alumno`", indexes = { @Index(columnList = "expedienteCerrado") })
public class Alumno {

	// Attributes -------------------------------------------------------------

	private boolean expedienteCerrado;

	// Constructors -----------------------------------------------------------

	public Alumno() {
		super();
	}

	// Getters and setters ----------------------------------------------------

	
	public boolean getExpedienteCerrado() {
		return this.expedienteCerrado;
	}

	public void setExpedienteCerrado(final boolean expedienteCerrado) {
		this.expedienteCerrado = expedienteCerrado;
	}

	// Relationships ----------------------------------------------------------

	private Oferta ofertaAsignada;
	private Tutor tutorAsignado;

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Oferta getOfertaAsignada() {
		return this.ofertaAsignada;
	}

	public void setOfertaAsignada(final Oferta ofertaAsignada) {
		this.ofertaAsignada = ofertaAsignada;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Tutor getTutorAsignado() {
		return this.tutorAsignado;
	}

	public void setTutorAsignado(final Tutor tutorAsignado) {
		this.tutorAsignado = tutorAsignado;
	}

}
