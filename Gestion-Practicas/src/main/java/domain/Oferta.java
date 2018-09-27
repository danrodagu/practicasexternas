
package domain;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Oferta extends DomainEntity {

	private String titulo;
	private String descripcion;
	private boolean esCurricular; // True: curricular; False: extracurricular
	private BigDecimal duracion; // En meses
	private BigDecimal dotacion;
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

	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	@NotNull
	@NotBlank
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean getEsCurricular() {
		return this.esCurricular;
	}

	public void setEsCurricular(final boolean esCurricular) {
		this.esCurricular = esCurricular;
	}

	// mínimo real 1.5, máximo 6	
	public BigDecimal getDuracion() {
		return this.duracion;
	}

	public void setDuracion(final BigDecimal duracion) {
		this.duracion = duracion;
	}
		
	public BigDecimal getDotacion() {
		return this.dotacion;
	}

	public void setDotacion(final BigDecimal dotacion) {
		this.dotacion = dotacion;
	}

	@NotNull
	@NotBlank
	public String getPais() {
		return this.pais;
	}

	public void setPais(final String pais) {
		this.pais = pais;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(final String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(final String provincia) {
		this.provincia = provincia;
	}

	@NotNull
	@NotBlank
	public String getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(final String empresa) {
		this.empresa = empresa;
	}

	// Relationships ----------------------------------------------------------

}
