package forms;

import org.hibernate.validator.constraints.SafeHtml;

public class BusquedaAdministrativosForm {

	// Attributes -------------------------------------------------------------

	private String nif;
	private String nombre;
	private String apellidos;
	private Boolean activo;

	// Constructors -----------------------------------------------------------

	public BusquedaAdministrativosForm() {
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
	
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(final Boolean activo) {
		this.activo = activo;
	}
	

}
