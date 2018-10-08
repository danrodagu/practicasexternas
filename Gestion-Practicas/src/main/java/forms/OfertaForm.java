
package forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class OfertaForm {

	// Attributes -------------------------------------------------------------

	private int id;
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

	public OfertaForm() {
		super();
		this.id = 0;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}
	
	@NotNull
	@NotBlank
	@SafeHtml
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	@NotNull
	@NotBlank
	@SafeHtml
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

	@NotNull
	@Min(1) // m�nimo real 1.5
	@Max(6)
	@SafeHtml
	public double getDuracion() {
		return this.duracion;
	}

	public void setDuracion(final double duracion) {
		this.duracion = duracion;
	}

	@NotNull
	@Min(0)
	@SafeHtml
	public double getDotacion() {
		return this.dotacion;
	}

	public void setDotacion(final double dotacion) {
		this.dotacion = dotacion;
	}

	@NotNull
	@NotBlank
	@SafeHtml
	public String getPais() {
		return this.pais;
	}

	public void setPais(final String pais) {
		this.pais = pais;
	}

	@SafeHtml
	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(final String localidad) {
		this.localidad = localidad;
	}

	@SafeHtml
	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(final String provincia) {
		this.provincia = provincia;
	}

	@NotNull
	@NotBlank
	@SafeHtml
	public String getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(final String empresa) {
		this.empresa = empresa;
	}

}