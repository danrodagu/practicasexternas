package forms;

import org.hibernate.validator.constraints.SafeHtml;

public class BusquedaAlumnosForm {

	// Attributes -------------------------------------------------------------

	private String nif;
	private String nombre;
	private String apellidos;
	private String titulacion;
	
	private Boolean tienePracticaAbierta;
	private Boolean activo;
	

	// Constructors -----------------------------------------------------------

	public BusquedaAlumnosForm() {
		super();
	}

	@SafeHtml
	public String getNif() {
		return nif;
	}

	public void setNif(final String nif) {
		this.nif = nif;
	}

	@SafeHtml
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	@SafeHtml
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(final String apellidos) {
		this.apellidos = apellidos;
	}

	@SafeHtml
	public String getTitulacion() {
		return titulacion;
	}

	public void setTitulacion(final String titulacion) {
		this.titulacion = titulacion;
	}
	
	public Boolean getTienePracticaAbierta() {
		return tienePracticaAbierta;
	}

	public void setTienePracticaAbierta(final Boolean tienePracticaAbierta) {
		this.tienePracticaAbierta = tienePracticaAbierta;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(final Boolean activo) {
		this.activo = activo;
	}	

	

}
