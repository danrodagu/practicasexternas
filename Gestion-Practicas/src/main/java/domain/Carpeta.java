package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Carpeta extends DomainEntity {

	private String nombre;
	private boolean noModificable;

	// Constructor ------------------------------------------------------------

	public Carpeta() {
		super();
	}

	// Attributes -------------------------------------------------------------

	@NotBlank
	@NotNull
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isNoModificable() {
		return this.noModificable;
	}

	public void setNoModificable(boolean noModificable) {
		this.noModificable = noModificable;
	}

	// Relationships ---------------------------------------------------------
	private Actor actor;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

}
