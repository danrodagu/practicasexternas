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
		
		Assert.isTrue(actorService.isAdministrativo() || actorService.isCoordinador());		
		

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
	
	@RequestMapping(value = "/evaluar", method = RequestMethod.GET)
	public ResponseEntity<Object> mensajeFeedback(@RequestParam(value = "ofertaId", required = true) final int ofertaId, final HttpServletRequest request) {
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
		
		mensajeForm = new MensajeForm();
		mensajeForm.setAsunto("PETICIÓN DE EVALUACIÓN");
		mensajeForm.setCuerpo("Se requiere subir la evaluación de la empresa para la siguiente práctica: http://" + dominio + "/Gestion-Practicas/oferta/display.do?ofertaId=" + ofertaId + 
				" \r\r No olvide pulsar el botón 'Cerrar documentación' tras la subida correspondiente."
				+ "\r\r - Este mensaje ha sido generado automáticamente -");
		mensajeForm.setIdReceptor(administrativoService.findAll().iterator().next().getId());
		
		this.mensajeService.createMensaje(mensajeForm);	
			
		return new ResponseEntity<Object>(body, headers, HttpStatus.OK);
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
