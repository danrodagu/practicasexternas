
package forms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

public class OfertaForm {

	// Attributes -------------------------------------------------------------

	private Integer id;
	
	//ALUMNO
	private Integer idAlumno;
	
	//OFERTA
	private String titulo;
	private String descripcion;
	private boolean esCurricular; // True: curricular; False: extracurricular
	private BigDecimal duracion; // En meses
	private Integer horas;
	private Date fechaInicio;
	private Date fechaFin;
	private BigDecimal dotacion;
	private String pais;
	private String localidad;
	private String provincia;
	private String empresa;
	private String cifEmp;
	private String telefonoEmp;
	private String emailEmp;
	private String tutorEmp;
	
	//TUTOR
	private Integer idTutor;

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

	// m�nimo real 1.5, m�ximo 6.0	
	@NumberFormat
	public BigDecimal getDuracion() {
		return this.duracion;
	}

	public void setDuracion(final BigDecimal duracion) {
		this.duracion = duracion;
	}
	
	@NumberFormat
	public BigDecimal getDotacion() {
		return this.dotacion;
	}

	public void setDotacion(final BigDecimal dotacion) {
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

	@NotNull
	@NumberFormat
	public Integer getHoras() {
		return horas;
	}

	public void setHoras(final Integer horas) {
		this.horas = horas;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(final Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(final Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@NotNull
	@NotBlank
	@SafeHtml
	public String getCifEmp() {
		return cifEmp;
	}

	public void setCifEmp(final String cifEmp) {
		this.cifEmp = cifEmp;
	}

	@NotNull
	@NotBlank
	@SafeHtml
	public String getTelefonoEmp() {
		return telefonoEmp;
	}

	public void setTelefonoEmp(final String telefonoEmp) {
		this.telefonoEmp = telefonoEmp;
	}

	@NotNull
	@NotBlank
	@Email
	@SafeHtml
	public String getEmailEmp() {
		return emailEmp;
	}

	public void setEmailEmp(final String emailEmp) {
		this.emailEmp = emailEmp;
	}

	@NotNull
	@NotBlank
	@SafeHtml
	public String getTutorEmp() {
		return tutorEmp;
	}

	public void setTutorEmp(final String tutorEmp) {
		this.tutorEmp = tutorEmp;
	}

	@NotNull
	public Integer getIdTutor() {
		return idTutor;
	}
	
	public void setIdTutor(final Integer idTutor) {
		this.idTutor = idTutor;
	}

	@NotNull
	public Integer getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(final Integer idAlumno) {
		this.idAlumno = idAlumno;
	}

}
