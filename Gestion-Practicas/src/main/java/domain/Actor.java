
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	//COMUNES
	private String nif;
	private String nombre;
	private String apellidos;
	private String email;
	private boolean activo;
	
	//ALUMNO
	private String titulacion;
	
	//TUTOR
	private String departamento;

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
	
	@NotNull
	@NotBlank
	public String getNif() {
		return nif;
	}

	public void setNif(final String nif) {
		this.nif = nif;
	}

	@Email
	@NotNull
	@NotBlank
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(final boolean activo) {
		this.activo = activo;
	}

	public String getTitulacion() {
		return titulacion;
	}

	public void setTitulacion(final String titulacion) {
		this.titulacion = titulacion;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(final String departamento) {
		this.departamento = departamento;
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

	

}
