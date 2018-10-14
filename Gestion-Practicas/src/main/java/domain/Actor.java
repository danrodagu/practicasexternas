
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	//COMUNES
	private String nombre;
	private String apellidos;
	
	//ALUMNO
	private Boolean expedienteCerrado;

	// Constructors -----------------------------------------------------------

	public Actor() {
		super();
	}

	// Getters and setters ----------------------------------------------------

	@NotNull
	@NotBlank
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	@NotNull
	@NotBlank
	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(final String apellidos) {
		this.apellidos = apellidos;
	}	
	
	@Transient
	public String getNombreCompleto() {		
		return getApellidos() + ", " + getNombre();
	}
	
	
	//ALUMNO
	public Boolean getExpedienteCerrado() {
		return this.expedienteCerrado;
	}

	public void setExpedienteCerrado(final Boolean expedienteCerrado) {
		this.expedienteCerrado = expedienteCerrado;
	}

	// Relationships ----------------------------------------------------------

	private UserAccount userAccount;

	@Valid
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	
	//ALUMNO
	private Oferta ofertaAsignada;
	private Actor tutorAsignado;

//	@NotNull
	@Valid
	@OneToOne(optional = true)
	public Oferta getOfertaAsignada() {
		return this.ofertaAsignada;
	}

	public void setOfertaAsignada(final Oferta ofertaAsignada) {
		this.ofertaAsignada = ofertaAsignada;
	}

//	@NotNull
	@Valid
	@ManyToOne(optional = true)
	public Actor getTutorAsignado() {
		return this.tutorAsignado;
	}

	public void setTutorAsignado(final Actor tutorAsignado) {
		this.tutorAsignado = tutorAsignado;
	}
	

}
