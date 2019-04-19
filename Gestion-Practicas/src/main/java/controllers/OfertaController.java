package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Oferta;
import forms.MensajeForm;
import forms.OfertaForm;
import services.ActorService;
import services.AdministrativoService;
import services.AlumnoService;
import services.MensajeService;
import services.OfertaService;
import services.TutorService;

@Controller
@RequestMapping("/oferta")
public class OfertaController extends AbstractController {

	@PersistenceContext( unitName="Gestion-Practicas" )
	private EntityManager em;
	
	// Services ---------------------------------------------------------------

	@Autowired
	private OfertaService ofertaService;
	
	@Autowired
	private AlumnoService alumnoService;
	
	@Autowired
	private AdministrativoService administrativoService;
	
	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private MensajeService	mensajeService;

	// Constructors -----------------------------------------------------------

	public OfertaController() {
		super();
	}
	
	
	// Listing --------------------------------------
//	@RequestMapping(value = "/list")
//	public ModelAndView list(final HttpServletRequest request) {
//		ModelAndView result;
//		Collection<Actor> alumnos;
//		
//		HttpSession session = request.getSession();
//		session.setAttribute("active", "alumnos");	
//		
//		if(actorService.isTutor()) {
//			alumnos = this.tutorService.findMyStudents();
//		} else {
//			alumnos = alumnoService.findAll();
//		}
//
//		
//		result = new ModelAndView("alumno/list");
//
//		result.addObject("alumnos", alumnos);
//
//		return result;
//	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) final int ofertaId) {
		ModelAndView result;
		Oferta oferta;
		
		oferta = this.ofertaService.findOne(ofertaId);		

		Assert.notNull(oferta);

		result = new ModelAndView("oferta/display");

		result.addObject("oferta", oferta);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final HttpServletRequest request) {
		ModelAndView result;
		OfertaForm ofertaForm;
		Collection<Actor> tutores = new ArrayList<>();
		Collection<Actor> alumnos = new ArrayList<>();

		HttpSession session = request.getSession();
		session.setAttribute("active", "alta");

		ofertaForm = new OfertaForm();
		tutores = tutorService.findAll();
		alumnos = alumnoService.findAll();
		
		result = new ModelAndView("oferta/create");
		result.addObject("ofertaForm", ofertaForm);
		result.addObject("tutores", tutores);
		result.addObject("alumnos", alumnos);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) final int ofertaId) {
		ModelAndView result;
		Oferta oferta;
		OfertaForm ofertaForm;
		Actor actor;
		
		actor = actorService.findByPrincipal();
		
		Assert.isTrue(actorService.isAdministrativo(actor.getId()) || actorService.isCoordinador(actor.getId()));		
		

		oferta = this.ofertaService.findOne(ofertaId);
		Assert.notNull(oferta);

		ofertaForm = this.ofertaService.takeForm(oferta);
		result = this.createEditModelAndView(ofertaForm, null);

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid final OfertaForm ofertaForm, final BindingResult bindingResult) {

		ModelAndView result;
		
		try {			
			Integer ofertasAsignadas = ofertaService.ofertasByAlumno(ofertaForm.getIdAlumno()).size();
			Assert.isTrue(ofertasAsignadas < 2);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(ofertaForm, "oferta.numOfertas.error");
			return result;
		}
		
		try {
			if(ofertaForm.getEsCurricular()) {
				Integer ofertasCurricAsignadas = ofertaService.ofertasCurricByAlumno(ofertaForm.getIdAlumno()).size();
				Assert.isTrue(ofertasCurricAsignadas == 0);
			}			
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(ofertaForm, "oferta.numCurric.error");
			return result;
		}
		
		try {
			if(!ofertaForm.getEsCurricular()) {
				Integer ofertasExtraAsignadas = ofertaService.ofertasExtraByAlumno(ofertaForm.getIdAlumno()).size();
				Assert.isTrue(ofertasExtraAsignadas == 0);
			}			
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(ofertaForm, "oferta.numExtra.error");
			return result;
		}
		
		try {
			Assert.isTrue(ofertaForm.getDuracion().doubleValue() >= 1.5 && ofertaForm.getDuracion().doubleValue() <= 6.0);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(ofertaForm, "oferta.duracion.error");
			return result;
		}
		
		try {
			Assert.isTrue(ofertaForm.getDotacion().doubleValue() >= 0.0);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(ofertaForm, "oferta.dotacion.error");
			return result;
		}

		if (bindingResult.hasErrors()) {
			result = this.createEditModelAndView(ofertaForm, null);
		} else {
			try {
				this.ofertaService.registrarOferta(ofertaForm);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(ofertaForm, "actor.commit.error");
			}
		}

		return result;

	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final OfertaForm ofertaForm, final BindingResult bindingResult) {

		ModelAndView result;
		Oferta oferta;
				
		try {
			if(ofertaForm.getEsCurricular()) {
				Integer ofertasCurricAsignadas = ofertaService.ofertasCurricByAlumnoEdit(ofertaForm.getIdAlumno(), ofertaForm.getId()).size();
				Assert.isTrue(ofertasCurricAsignadas == 0);
			}			
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(ofertaForm, "oferta.numCurric.error");
			return result;
		}
		
		try {
			if(!ofertaForm.getEsCurricular()) {
				Integer ofertasExtraAsignadas = ofertaService.ofertasExtraByAlumnoEdit(ofertaForm.getIdAlumno(), ofertaForm.getId()).size();
				Assert.isTrue(ofertasExtraAsignadas == 0);
			}			
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(ofertaForm, "oferta.numExtra.error");
			return result;
		}
		
		try {
			Assert.isTrue(ofertaForm.getDuracion().doubleValue() >= 1.5 && ofertaForm.getDuracion().doubleValue() <= 6.0);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(ofertaForm, "oferta.duracion.error");
			return result;
		}
		
		try {
			Assert.isTrue(ofertaForm.getDotacion().doubleValue() >= 0.0);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(ofertaForm, "oferta.dotacion.error");
			return result;
		}

		if (bindingResult.hasErrors()) {
			result = this.createEditModelAndView(ofertaForm, null);
		} else {
			try {
				oferta = this.ofertaService.reconstructEdit(ofertaForm);
				this.ofertaService.save(oferta);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(ofertaForm, "actor.commit.error");
			}
		}

		return result;

	}

	// Ancillary methods ------------------------------------------------------
	
	@RequestMapping(value = "/peticionEvaluacion", method = RequestMethod.GET)
	public ResponseEntity<Object> peticionEvaluacion(@RequestParam(value = "ofertaId", required = true) final int ofertaId, final HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		String body = null;
		Oferta oferta;
		MensajeForm mensajeForm;
		Map<String, Object> propiedades = em.getEntityManagerFactory().getProperties();
		String dominio = "";
		
		dominio = propiedades.get("javax.persistence.jdbc.url").toString(); // jdbc:mysql://localhost:3306/Gestion-Practicas?useSSL=false
		dominio = dominio.substring(dominio.indexOf("jdbc:mysql://") + 13, dominio.indexOf("/Gestion-Practicas?useSSL=false"));
				
		oferta = ofertaService.findOne(ofertaId);
		oferta.setEnEvaluacion(true);
		
		for(Actor a : administrativoService.findAllActivos()) {
			mensajeForm = new MensajeForm();
			mensajeForm.setAsunto("PETICI�N DE EVALUACI�N");
			mensajeForm.setCuerpo("Se requiere subir la evaluaci�n de la empresa para la siguiente pr�ctica: http://" + dominio + "/Gestion-Practicas/oferta/display.do?ofertaId=" + ofertaId + 
					" <br /><br /> Compruebe que no falte ning�n documento necesario para la evaluaci�n y pulse el bot�n 'Cerrar documentaci�n' tras la subida correspondiente."
					+ "<br /><br /> NOTA: No lleve a cabo este proceso si otro administrativo ya lo ha realizado."
					+ "<br /><br /> - Este mensaje ha sido generado autom�ticamente -");
			mensajeForm.setIdReceptor(a.getId());
			
			this.mensajeService.createMensaje(mensajeForm);
		}			
			
		return new ResponseEntity<Object>(body, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/cerrarDocumentacion", method = RequestMethod.GET)
	public ModelAndView cerrarDocumentacion(@RequestParam(required = true) final int ofertaId) {
		ModelAndView result;
		Oferta oferta;
		MensajeForm mensajeForm;
		Map<String, Object> propiedades = em.getEntityManagerFactory().getProperties();
		String dominio = "";
		
		dominio = propiedades.get("javax.persistence.jdbc.url").toString(); // jdbc:mysql://localhost:3306/Gestion-Practicas?useSSL=false
		dominio = dominio.substring(dominio.indexOf("jdbc:mysql://") + 13, dominio.indexOf("/Gestion-Practicas?useSSL=false"));
		
		oferta = ofertaService.findOne(ofertaId);
		
		mensajeForm = new MensajeForm();
		mensajeForm.setAsunto("PETICI�N DE EVALUACI�N");
		mensajeForm.setCuerpo("Se requiere evaluaci�n para la siguiente pr�ctica: http://" + dominio + "/Gestion-Practicas/oferta/display.do?ofertaId=" + ofertaId + 
				" <br /><br /> Se ha habilitado el formulario de evaluaci�n. "
				+ "<br /><br /> - Este mensaje ha sido generado autom�ticamente -");
		mensajeForm.setIdReceptor(oferta.getTutorAsignado().getId());			
		
		result = new ModelAndView("redirect:/documento/list.do?ofertaId=" + oferta.getId());

		try {			
			ofertaService.cerrarDocumentacion(ofertaId);
			this.mensajeService.createMensaje(mensajeForm);
		} catch (Throwable oops) {
			result.addObject("message", "oferta.cerrarDocu.error");
		}		

		return result;
	}
	
	@RequestMapping(value = "/abrirDocumentacion", method = RequestMethod.GET)
	public ModelAndView abrirDocumentacion(@RequestParam(required = true) final int ofertaId) {
		ModelAndView result;
		Oferta oferta;
		
		oferta = ofertaService.findOne(ofertaId);
		
		result = new ModelAndView("redirect:/documento/list.do?ofertaId=" + oferta.getId());

		try {			
			ofertaService.abrirDocumentacion(ofertaId);			
		} catch (Throwable oops) {
			result.addObject("message", "oferta.abrirDocu.error");
		}		

		return result;
	}
	
	@RequestMapping(value = "/notificarCierreExp", method = RequestMethod.GET)
	public ModelAndView notificarCierreExp(@RequestParam(required = true) final int ofertaId) {
		ModelAndView result;
		Oferta oferta;
		
		MensajeForm mensajeForm;
		Map<String, Object> propiedades = em.getEntityManagerFactory().getProperties();
		String dominio = "";
		
		dominio = propiedades.get("javax.persistence.jdbc.url").toString(); // jdbc:mysql://localhost:3306/Gestion-Practicas?useSSL=false
		dominio = dominio.substring(dominio.indexOf("jdbc:mysql://") + 13, dominio.indexOf("/Gestion-Practicas?useSSL=false"));
		
		oferta = ofertaService.findOne(ofertaId);		
		
		result = new ModelAndView("redirect:/documento/list.do?ofertaId=" + oferta.getId());

		try {			
			ofertaService.actaFirmada(ofertaId);
			
			for(Actor a : administrativoService.findAllActivos()) {
				mensajeForm = new MensajeForm();
				mensajeForm.setAsunto("PETICI�N DE CIERRE DE EXPEDIENTE");
				mensajeForm.setCuerpo("Se requiere cierre de expediente para la siguiente pr�ctica: http://" + dominio + "/Gestion-Practicas/oferta/display.do?ofertaId=" + ofertaId + 
						" <br /><br /> Se ha habilitado para ello el bot�n 'Cerrar expediente'. No olvide revisar que el acta est� correctamente firmada. "
						+ "<br /><br /> - Este mensaje ha sido generado autom�ticamente -");
				mensajeForm.setIdReceptor(a.getId());
				
				this.mensajeService.createMensaje(mensajeForm);
			}
		} catch (Throwable oops) {
			result.addObject("message", "oferta.error");
		}

		return result;
	}
	
	@RequestMapping(value = "/cerrarExpediente", method = RequestMethod.GET)
	public ModelAndView cerrarExpediente(@RequestParam(required = true) final int ofertaId) {
		ModelAndView result;
		Oferta oferta;
		
		oferta = ofertaService.findOne(ofertaId);
		
		result = new ModelAndView("redirect:/oferta/display.do?ofertaId=" + oferta.getId());

		try {			
			ofertaService.cerrarExpediente(ofertaId);			
		} catch (Throwable oops) {
			result.addObject("message", "oferta.error");
		}		

		return result;
	}
	
	protected ModelAndView createEditModelAndView(final OfertaForm ofertaForm, final String message) {
		ModelAndView result;
		Collection<Actor> tutores = new ArrayList<>();
		Collection<Actor> alumnos = new ArrayList<>();
		
		tutores = tutorService.findAll();
		alumnos = alumnoService.findAll();
		
		result = new ModelAndView("oferta/edit");
		
		if(ofertaForm.getId() == 0) {
			result.addObject("action", "oferta/create.do");
		}else {			
			result.addObject("action", "oferta/edit.do");
		}
		
		result.addObject("ofertaForm", ofertaForm);
		result.addObject("tutores", tutores);
		result.addObject("alumnos", alumnos);
		result.addObject("message", message);

		return result;
	}

}
