
package forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class RegistroAlumnoForm {

	// Attributes -------------------------------------------------------------

	//ALUMNO
	private String nombre;
	private String apellidos;
	private String password;
	private String password2;
	private String username;
	
	//OFERTA
	private String titulo;
	private String descripcion;
	private boolean esCurricular; // True: curricular; False: extracurricular
	private double duracion; // En meses
	private double dotacion;
	private String pais;
	private String localidad;
	private String provincia;
	private String empresa;
	
	//TUTOR
	private int idTutor;

	// Constructors -----------------------------------------------------------

	public RegistroAlumnoForm() {
		super();
	}

	@NotNull
	@NotBlank
	@SafeHtml
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	@NotNull
	@NotBlank
	@SafeHtml
	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(final String apellidos) {
		this.apellidos = apellidos;
	}

	@NotNull
	@NotBlank
	@Size(min = 5, max = 32)
	@SafeHtml
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Size(min = 5, max = 32)
	@SafeHtml
	public String getPassword2() {
		return this.password2;
	}

	public void setPassword2(final String password2) {
		this.password2 = password2;
	}

	@NotNull
	@Size(min = 5, max = 32)
	@SafeHtml
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
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

	public int getIdTutor() {
		return idTutor;
	}

	@NotNull
	@NotBlank
	public void setIdTutor(final int idTutor) {
		this.idTutor = idTutor;
	}

}
