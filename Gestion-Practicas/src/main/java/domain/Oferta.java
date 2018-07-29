
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Oferta extends DomainEntity {

	private String titulo;
	private String descripcion;
	private boolean esCurricular; // True: curricular; False: extracurricular
	private double duracion; // En meses
	private double dotacion;
	private String pais;
	private String localidad;
	private String provincia;
	private String empresa;

	// Constructors -----------------------------------------------------------

	public Oferta() {
		super();
	}

	// Getters and setters ----------------------------------------------------

	@NotNull
	@NotBlank
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@NotNull
	@NotBlank
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean getEsCurricular() {
		return this.esCurricular;
	}

	public void setEsCurricular(boolean esCurricular) {
		this.esCurricular = esCurricular;
	}

	@NotNull
	@Min(1) // mínimo real 1.5
	@Max(6)
	public double getDuracion() {
		return this.duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	@NotNull
	@Min(0)
	public double getDotacion() {
		return this.dotacion;
	}

	public void setDotacion(double dotacion) {
		this.dotacion = dotacion;
	}

	@NotNull
	@NotBlank
	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@NotNull
	@NotBlank
	public String getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	// Relationships ----------------------------------------------------------

}
