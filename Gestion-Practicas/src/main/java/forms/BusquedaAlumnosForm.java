package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

public class BusquedaAlumnosForm {

	// Attributes -------------------------------------------------------------

	private String nif;
	private String nombre;
	private String apellidos;
	private String titulacion;
	
	private Boolean tienePracticaAbierta;
	private Boolean activo;
	
	//OFERTA
	private String empresa;
	private String cifEmp;
	private Boolean esCurricular;
	private String duracion;
	private String horas;
	private Date fechaInicioDesde;
	private Date fechaInicioHasta;
	private Date fechaFinDesde;
	private Date fechaFinHasta;
	private String dotacion;	
	

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

	@SafeHtml
	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(final String empresa) {
		this.empresa = empresa;
	}

	@SafeHtml
	public String getCifEmp() {
		return cifEmp;
	}

	public void setCifEmp(final String cifEmp) {
		this.cifEmp = cifEmp;
	}

	public Boolean getEsCurricular() {
		return esCurricular;
	}

	public void setEsCurricular(final Boolean esCurricular) {
		this.esCurricular = esCurricular;
	}
	
	
	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(final String duracion) {
		this.duracion = duracion;
	}
	

	public String getHoras() {
		return horas;
	}

	public void setHoras(final String horas) {
		this.horas = horas;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getFechaInicioDesde() {
		return fechaInicioDesde;
	}

	public void setFechaInicioDesde(final Date fechaInicioDesde) {
		this.fechaInicioDesde = fechaInicioDesde;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getFechaInicioHasta() {
		return fechaInicioHasta;
	}

	public void setFechaInicioHasta(final Date fechaInicioHasta) {
		this.fechaInicioHasta = fechaInicioHasta;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getFechaFinDesde() {
		return fechaFinDesde;
	}

	public void setFechaFinDesde(final Date fechaFinDesde) {
		this.fechaFinDesde = fechaFinDesde;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getFechaFinHasta() {
		return fechaFinHasta;
	}

	public void setFechaFinHasta(final Date fechaFinHasta) {
		this.fechaFinHasta = fechaFinHasta;
	}
	

	
	public String getDotacion() {
		return dotacion;
	}

	public void setDotacion(final String dotacion) {
		this.dotacion = dotacion;
	}	

	

}
