
package domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Oferta extends DomainEntity {

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
	
	private boolean enEvaluacion;
	private boolean docuCerrada;
	private boolean evaluada;
	private boolean preacta;
	private boolean actaFirmada;
	private boolean expedienteCerrado;
	
	@Transient
    private String esCurricularStr;
	
	@Transient
    private String expedienteCerradoStr;
	

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
	public String getCifEmp() {
		return cifEmp;
	}

	public void setCifEmp(final String cifEmp) {
		this.cifEmp = cifEmp;
	}

	@NotNull
	@NotBlank
	public String getTelefonoEmp() {
		return telefonoEmp;
	}

	public void setTelefonoEmp(final String telefonoEmp) {
		this.telefonoEmp = telefonoEmp;
	}

	@NotNull
	@NotBlank
	@Email
	public String getEmailEmp() {
		return emailEmp;
	}

	public void setEmailEmp(final String emailEmp) {
		this.emailEmp = emailEmp;
	}

	@NotNull
	@NotBlank
	public String getTutorEmp() {
		return tutorEmp;
	}

	public void setTutorEmp(final String tutorEmp) {
		this.tutorEmp = tutorEmp;
	}
	
	public boolean isEnEvaluacion() {
		return enEvaluacion;
	}

	public void setEnEvaluacion(final boolean enEvaluacion) {
		this.enEvaluacion = enEvaluacion;
	}

	public boolean isDocuCerrada() {
		return docuCerrada;
	}

	public void setDocuCerrada(final boolean docuCerrada) {
		this.docuCerrada = docuCerrada;
	}

	public boolean isEvaluada() {
		return evaluada;
	}

	public void setEvaluada(final boolean evaluada) {
		this.evaluada = evaluada;
	}

	public boolean isPreacta() {
		return preacta;
	}

	public void setPreacta(final boolean preacta) {
		this.preacta = preacta;
	}

	public boolean isActaFirmada() {
		return actaFirmada;
	}

	public void setActaFirmada(final boolean actaFirmada) {
		this.actaFirmada = actaFirmada;
	}

	public Boolean getExpedienteCerrado() {
		return this.expedienteCerrado;
	}

	public void setExpedienteCerrado(final Boolean expedienteCerrado) {
		this.expedienteCerrado = expedienteCerrado;
	}
	
	@Transient
	public String getEsCurricularStr() {
		esCurricularStr = "";
		
		if(esCurricular) {
			esCurricularStr = "Curricular";
		}else {
			esCurricularStr = "Extracurricular";
		}
		
		return esCurricularStr;
	}
	
	public void setEsCurricularStr(final String esCurricularStr) {
		this.esCurricularStr = esCurricularStr;
	}
	
	@Transient
	public String getExpedienteCerradoStr() {		
		expedienteCerradoStr = "";
		
		if(expedienteCerrado) {
			expedienteCerradoStr = "Cerrado";
		}else {
			expedienteCerradoStr = "Abierto";
		}
		return expedienteCerradoStr;
	}

	public void setExpedienteCerradoStr(final String expedienteCerradoStr) {
		this.expedienteCerradoStr = expedienteCerradoStr;
	}



	// Relationships ----------------------------------------------------------
	private Actor alumnoAsignado;
	private Actor tutorAsignado;

	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getAlumnoAsignado() {
		return alumnoAsignado;
	}

	public void setAlumnoAsignado(final Actor alumnoAsignado) {
		this.alumnoAsignado = alumnoAsignado;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getTutorAsignado() {
		return this.tutorAsignado;
	}

	public void setTutorAsignado(final Actor tutorAsignado) {
		this.tutorAsignado = tutorAsignado;
	}
	
	
}
