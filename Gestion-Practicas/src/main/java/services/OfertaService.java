
package services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Documento;
import domain.Oferta;
import domain.Valoracion;
import forms.InvalidaEvaluacionForm;
import forms.MensajeForm;
import forms.OfertaForm;
import repositories.OfertaRepository;

@Service
@Transactional
public class OfertaService {

	@PersistenceContext( unitName="Gestion-Practicas" )
	private EntityManager em;
	
	// Managed repository -----------------------------------------------------

	@Autowired
	private OfertaRepository ofertaRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private AlumnoService alumnoService;
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private ValoracionService valoracionService;
	
	@Autowired
	private MensajeService mensajeService;
	
	@Autowired
	private UtilService	utilService;
	
	// Constructors -----------------------------------------------------------

	public OfertaService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Oferta create(final Oferta nuevaOferta) {
		Oferta res;

		res = new Oferta();

		res.setDescripcion(nuevaOferta.getDescripcion());
		res.setDotacion(nuevaOferta.getDotacion());
		res.setDuracion(nuevaOferta.getDuracion());
		res.setEmpresa(nuevaOferta.getEmpresa());
		res.setEsCurricular(nuevaOferta.getEsCurricular());
		res.setLocalidad(nuevaOferta.getLocalidad());
		res.setProvincia(nuevaOferta.getProvincia());
		res.setPais(nuevaOferta.getPais());
		res.setTitulo(nuevaOferta.getTitulo());

		return res;

	}

	public Oferta findOne(final int ofertaId) {
		Oferta result;

		result = this.ofertaRepository.findOne(ofertaId);

		return result;
	}

	public Collection<Oferta> findAll() {
		Collection<Oferta> result;

		result = this.ofertaRepository.findAll();

		return result;
	}

	public Oferta save(final Oferta oferta) {
		Oferta result;

		Assert.notNull(oferta);

		result = this.ofertaRepository.save(oferta);

		return result;

	}

//	public void delete(final Oferta oferta) {
//
//		Assert.notNull(oferta);
//
//		this.ofertaRepository.delete(oferta);
//
//	}

	// Other business methods -------------------------------------------------


	public Collection<Oferta> ofertasByEmpresa(final String nombreEmpresa) {
		final Collection<Oferta> result;

//		Assert.isTrue(this.actorService.isCoordinador());

		result = this.ofertaRepository.ofertasByEmpresa(nombreEmpresa);

		return result;

	}


	public Collection<Oferta> ofertasByPais(final String pais) {
		final Collection<Oferta> result;

		result = this.ofertaRepository.ofertasByPais(pais);

		return result;

	}
	
	public Collection<Oferta> ofertasByAlumno(final int idAlumno) {
		final Collection<Oferta> result;

		result = this.ofertaRepository.ofertasByAlumno(idAlumno);

		return result;

	}
	
	public Collection<Oferta> ofertasByAlumnoTutor(final int idAlumno, final int idTutor) {
		final Collection<Oferta> result;

		result = this.ofertaRepository.ofertasByAlumnoTutor(idAlumno, idTutor);

		return result;

	}
	
	public Collection<Oferta> ofertasCurricByAlumno(final int idAlumno) {
		final Collection<Oferta> result;

		result = this.ofertaRepository.ofertasCurricByAlumno(idAlumno);

		return result;

	}
	
	public Collection<Oferta> ofertasExtraByAlumno(final int idAlumno) {
		final Collection<Oferta> result;

		result = this.ofertaRepository.ofertasExtraByAlumno(idAlumno);

		return result;

	}
	
	public Collection<Oferta> ofertasCurricByAlumnoEdit(final int idAlumno, final int idOferta) {
		final Collection<Oferta> result;

		result = this.ofertaRepository.ofertasCurricByAlumnoEdit(idAlumno, idOferta);

		return result;

	}
	
	public Collection<Oferta> ofertasExtraByAlumnoEdit(final int idAlumno, final int idOferta) {
		final Collection<Oferta> result;

		result = this.ofertaRepository.ofertasExtraByAlumnoEdit(idAlumno, idOferta);

		return result;

	}
	
	public void registrarOferta(final OfertaForm ofertaForm) {		
		Actor alumno;
		Oferta oferta;
		Actor tutor;

		tutor = tutorService.findOne(ofertaForm.getIdTutor());		
		
		alumno = alumnoService.findOne(ofertaForm.getIdAlumno());
		
		oferta = new Oferta();
		oferta.setTitulo(ofertaForm.getTitulo());
		oferta.setDescripcion(ofertaForm.getDescripcion());
		oferta.setDotacion(ofertaForm.getDotacion());
		oferta.setDuracion(ofertaForm.getDuracion());
		oferta.setHoras(ofertaForm.getHoras());
		oferta.setFechaInicio(ofertaForm.getFechaInicio());
		oferta.setFechaFin(ofertaForm.getFechaFin());
		oferta.setEmpresa(ofertaForm.getEmpresa());
		oferta.setCifEmp(ofertaForm.getCifEmp());
		oferta.setEmailEmp(ofertaForm.getEmailEmp());
		oferta.setTelefonoEmp(ofertaForm.getTelefonoEmp());
		oferta.setTutorEmp(ofertaForm.getTutorEmp());
		oferta.setEsCurricular(ofertaForm.getEsCurricular());
		oferta.setLocalidad(ofertaForm.getLocalidad());
		oferta.setProvincia(ofertaForm.getProvincia());
		oferta.setPais(ofertaForm.getPais());
		oferta.setExpedienteCerrado(false);
		oferta.setAlumnoAsignado(alumno);
		oferta.setTutorAsignado(tutor);
		
		oferta = save(oferta);
		
	}
	
	public OfertaForm takeForm(final Oferta oferta) {
		OfertaForm ofertaForm;

		ofertaForm = new OfertaForm();

		ofertaForm.setId(oferta.getId());
		ofertaForm.setTitulo(oferta.getTitulo());
		ofertaForm.setDescripcion(oferta.getDescripcion());
		ofertaForm.setDotacion(oferta.getDotacion());
		ofertaForm.setDuracion(oferta.getDuracion());
		ofertaForm.setHoras(oferta.getHoras());
		ofertaForm.setFechaInicio(oferta.getFechaInicio());
		ofertaForm.setFechaFin(oferta.getFechaFin());
		ofertaForm.setEmpresa(oferta.getEmpresa());
		ofertaForm.setCifEmp(oferta.getCifEmp());
		ofertaForm.setEmailEmp(oferta.getEmailEmp());
		ofertaForm.setTelefonoEmp(oferta.getTelefonoEmp());
		ofertaForm.setTutorEmp(oferta.getTutorEmp());
		ofertaForm.setEsCurricular(oferta.getEsCurricular());
		ofertaForm.setLocalidad(oferta.getLocalidad());
		ofertaForm.setProvincia(oferta.getProvincia());
		ofertaForm.setPais(oferta.getPais());
		ofertaForm.setIdAlumno(oferta.getAlumnoAsignado().getId());
		ofertaForm.setIdTutor(oferta.getTutorAsignado().getId());

		return ofertaForm;
	}
	
	public Oferta reconstructEdit(final OfertaForm ofertaForm) {
		Oferta res;
		Actor alumno;
		Actor tutor;
		
		Assert.isTrue(ofertaForm.getId() != 0);
		
		res = findOne(ofertaForm.getId());

		tutor = tutorService.findOne(ofertaForm.getIdTutor());		
		alumno = alumnoService.findOne(ofertaForm.getIdAlumno());	
		
		res.setTitulo(ofertaForm.getTitulo());
		res.setDescripcion(ofertaForm.getDescripcion());
		res.setDotacion(ofertaForm.getDotacion());
		res.setDuracion(ofertaForm.getDuracion());
		res.setHoras(ofertaForm.getHoras());
		res.setFechaInicio(ofertaForm.getFechaInicio());
		res.setFechaFin(ofertaForm.getFechaFin());
		res.setEmpresa(ofertaForm.getEmpresa());
		res.setCifEmp(ofertaForm.getCifEmp());
		res.setEmailEmp(ofertaForm.getEmailEmp());
		res.setTelefonoEmp(ofertaForm.getTelefonoEmp());
		res.setTutorEmp(ofertaForm.getTutorEmp());
		res.setEsCurricular(ofertaForm.getEsCurricular());
		res.setLocalidad(ofertaForm.getLocalidad());
		res.setProvincia(ofertaForm.getProvincia());
		res.setPais(ofertaForm.getPais());
		res.setAlumnoAsignado(alumno);
		res.setTutorAsignado(tutor);
		

		return res;
	}	
	
	public void cerrarDocumentacion(final int ofertaId) {
		Oferta oferta;
		
		oferta = this.findOne(ofertaId);
		
		Assert.isTrue(oferta.isEnEvaluacion());		
		
		oferta.setDocuCerrada(true);
	}
	
	public void abrirDocumentacion(final int ofertaId) {
		Oferta oferta;
		
		oferta = this.findOne(ofertaId);
		
		Assert.isTrue(oferta.isEnEvaluacion());
		Assert.isTrue(oferta.isDocuCerrada());
		
		oferta.setDocuCerrada(false);
	}
	
	public void evaluarOferta(final int ofertaId) {
		Oferta oferta;
		
		oferta = this.findOne(ofertaId);
		
		Assert.isTrue(oferta.isEnEvaluacion());
		Assert.isTrue(oferta.isDocuCerrada());
		
		oferta.setEvaluada(true);
	}
	
	public void preactaGenerada(final int ofertaId) {
		Oferta oferta;
		
		oferta = this.findOne(ofertaId);
		
		Assert.isTrue(oferta.isEnEvaluacion());
		Assert.isTrue(oferta.isDocuCerrada());
		Assert.isTrue(oferta.isEvaluada());
		
		oferta.setPreacta(true);
	}
	
	public void actaFirmada(final int ofertaId) {
		Oferta oferta;
		
		oferta = this.findOne(ofertaId);
		
		Assert.isTrue(oferta.isEnEvaluacion());
		Assert.isTrue(oferta.isDocuCerrada());
		Assert.isTrue(oferta.isEvaluada());
		Assert.isTrue(oferta.isPreacta());
		
		oferta.setActaFirmada(true);
	}
	
	public void cerrarExpediente(final int ofertaId) {
		Oferta oferta;
		
		oferta = this.findOne(ofertaId);
		
		Assert.isTrue(oferta.isEnEvaluacion());
		Assert.isTrue(oferta.isDocuCerrada());
		Assert.isTrue(oferta.isEvaluada());
		Assert.isTrue(oferta.isPreacta());
		Assert.isTrue(oferta.isActaFirmada());
		
		oferta.setExpedienteCerrado(true);
	}
	
	public void invalidarEvaluacion(final InvalidaEvaluacionForm invalidaEvaluacionForm, final HttpServletRequest request) {
		Oferta oferta;
		Collection<Documento> documentos;
		Valoracion valoracion;
		
		//Obtenemos la oferta, sus documentos y la valoración asociada si la hubiera
		oferta = findOne(invalidaEvaluacionForm.getIdOferta());
		documentos = documentoService.findDocumentosByOferta(oferta.getId());
		valoracion = valoracionService.findByOferta(oferta.getId());
		
		//Eliminamos las preactas/actas si las hubiera
		for(Documento d : documentos) {
			if(d.getTitulo().equals("ActaFirmada.pdf") || d.getTitulo().equals("ActaNoFirmada.pdf")) {
				documentoService.delete(d);
			}
		}
		
		//Eliminamos la valoración si la hubiera
		if(valoracion != null) {
			valoracionService.delete(valoracion);
		}
		
		//Anulamos por completo el proceso de evaluación
		oferta.setEnEvaluacion(false);
		oferta.setDocuCerrada(false);
		oferta.setEvaluada(false);
		oferta.setPreacta(false);
		oferta.setActaFirmada(false);
		oferta.setExpedienteCerrado(false);
		
		oferta = save(oferta);
		
		//Notificamos al alumno de la anulación de la evaluación
		MensajeForm mensajeForm;
		String dominio = "";
		String url = "";
		
		dominio = utilService.getDominio(request);
		url = "http://" + dominio + "/Gestion-Practicas/oferta/display.do?ofertaId=" + oferta.getId();
		
		mensajeForm = new MensajeForm();
		mensajeForm.setAsunto("ANULACIÓN DE EVALUACIÓN");
		mensajeForm.setCuerpo("Se ha anulado la evaluación para la siguiente práctica: <a href='" + url + "' target='_blank'>" + url + "</a>" 
				+ "<br />La justificación es la siguiente:"
				+ "<br /><br />"
				+ "<i>" + invalidaEvaluacionForm.getJustificacion() + "</i>"
				+ "<br /><br />"
				+ "Es necesario que vuelva a solicitar la evaluación de la práctica para que se lleve a cabo el proceso nuevamente."
				+ "<br /><br /> - Este mensaje ha sido generado automáticamente -");
		mensajeForm.setIdReceptor(oferta.getAlumnoAsignado().getId());
		
		mensajeService.createMensaje(mensajeForm);
	}

}
