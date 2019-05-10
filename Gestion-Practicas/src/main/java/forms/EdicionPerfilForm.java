
package forms;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.SafeHtml;

public class EdicionPerfilForm {

	// Attributes -------------------------------------------------------------

	private int id;
	private String nif;
	private String nombre;
	private String apellidos;
	private String email;
	
	// ALUMNO
	private String titulacion;
	
	// TUTOR
	private String departamento;
	

	private String password;
	private String password2;
	private String username;

	// Constructors -----------------------------------------------------------

	public EdicionPerfilForm() {
		super();
		this.id = 0;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}


	@SafeHtml
	@Size(min = 9, max = 9)
	public String getNif() {
		return nif;
	}

	public void setNif(final String nif) {
		this.nif = nif;
	}
	

	@SafeHtml
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}


	@SafeHtml
	public String getApellidos() {
		return this.apellidos;
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
	
	@SafeHtml
	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(final String departamento) {
		this.departamento = departamento;
	}


	@Email
	@SafeHtml
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}


	@SafeHtml
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@SafeHtml
	public String getPassword2() {
		return this.password2;
	}

	public void setPassword2(final String password2) {
		this.password2 = password2;
	}


	@SafeHtml
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

}
