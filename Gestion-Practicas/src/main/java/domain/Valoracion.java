
package domain;

import java.math.BigDecimal;

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

	private String texto;
	private BigDecimal notaCurricular;
	private String notaExtracurricular;

	// Constructors -----------------------------------------------------------

	public Valoracion() {
		super();
	}

	// Getters and setters ----------------------------------------------------

	@NotNull
	@NotBlank
	public String getTexto() {
		return this.texto;
	}

	public void setTexto(final String texto) {
		this.texto = texto;
	}

	public BigDecimal getNotaCurricular() {
		return notaCurricular;
	}

	public void setNotaCurricular(final BigDecimal notaCurricular) {
		this.notaCurricular = notaCurricular;
	}

	public String getNotaExtracurricular() {
		return notaExtracurricular;
	}

	public void setNotaExtracurricular(final String notaExtracurricular) {
		this.notaExtracurricular = notaExtracurricular;
	}


	// Relationships ----------------------------------------------------------

	
	private Oferta oferta;

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Oferta getOferta() {
		return this.oferta;
	}

	public void setOferta(final Oferta oferta) {
		this.oferta = oferta;
	}
	
}
