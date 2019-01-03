package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import forms.OfertaForm;
import forms.RegistroOfertaForm;
import services.AlumnoService;
import services.OfertaService;
import services.TutorService;

@Controller
@RequestMapping("/oferta")
public class OfertaController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private OfertaService ofertaService;
	
	@Autowired
	private AlumnoService alumnoService;
	
	@Autowired
	private TutorService tutorService;

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

//	@RequestMapping(value = "/display", method = RequestMethod.GET)
//	public ModelAndView display(@RequestParam(required = true, defaultValue = "0") int alumnoId) {
//		ModelAndView result;
//		Actor alumno;
//
//		if (alumnoId == 0) {
//			alumnoId = this.alumnoService.findByPrincipal().getId();
//			alumno = this.alumnoService.findOne(alumnoId);
//		} else {
//			alumno = this.alumnoService.findOne(alumnoId);
//		}
//
//		Assert.notNull(alumno);
//
//		result = new ModelAndView("alumno/display");
//
//		result.addObject("alumno", alumno);
//
//		return result;
//	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final HttpServletRequest request) {
		ModelAndView result;
		RegistroOfertaForm registroOfertaForm;
		Collection<Actor> tutores = new ArrayList<>();
		Collection<Actor> alumnos = new ArrayList<>();

		HttpSession session = request.getSession();
		session.setAttribute("active", "alta");

		registroOfertaForm = new RegistroOfertaForm();
		tutores = tutorService.findAll();
		alumnos = alumnoService.findAll();
		
		result = new ModelAndView("oferta/create");
		result.addObject("registroOfertaForm", registroOfertaForm);
		result.addObject("tutores", tutores);
		result.addObject("alumnos", alumnos);
		return result;
	}

	// Edition ----------------------------------------------------------------

//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
//	public ModelAndView edit() {
//		ModelAndView result;
//		Actor alumno;
//		OfertaForm ofertaForm;
//
//		alumno = this.alumnoService.findByPrincipal();
//		Assert.notNull(alumno);
//
//		ofertaForm = this.alumnoService.takeForm(alumno);
//		result = this.createEditModelAndView(ofertaForm);
//
//		return result;
//	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RegistroOfertaForm registroOfertaForm, final BindingResult bindingResult) {

		ModelAndView result;
		
		try {			
			Integer ofertasAsignadas = ofertaService.ofertasByAlumno(registroOfertaForm.getIdAlumno()).size();
			Assert.isTrue(ofertasAsignadas < 2);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(registroOfertaForm, "oferta.numOfertas.error");
			return result;
		}
		
		try {
			if(registroOfertaForm.getEsCurricular()) {
				Integer ofertasCurricAsignadas = ofertaService.ofertasCurricByAlumno(registroOfertaForm.getIdAlumno()).size();
				Assert.isTrue(ofertasCurricAsignadas == 0);
			}			
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(registroOfertaForm, "oferta.numCurric.error");
			return result;
		}
		
		try {
			if(!registroOfertaForm.getEsCurricular()) {
				Integer ofertasExtraAsignadas = ofertaService.ofertasExtraByAlumno(registroOfertaForm.getIdAlumno()).size();
				Assert.isTrue(ofertasExtraAsignadas == 0);
			}			
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(registroOfertaForm, "oferta.numExtra.error");
			return result;
		}
		
		try {
			Assert.isTrue(registroOfertaForm.getDuracion().doubleValue() >= 1.5 && registroOfertaForm.getDuracion().doubleValue() <= 6.0);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(registroOfertaForm, "oferta.duracion.error");
			return result;
		}
		
		try {
			Assert.isTrue(registroOfertaForm.getDotacion().doubleValue() >= 0.0);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(registroOfertaForm, "oferta.dotacion.error");
			return result;
		}
		
		// Añadir comprobacion de que la practica no sea la 3ra del alumno y que sea de tipo diferente a la 1ra que hizo

		if (bindingResult.hasErrors()) {
			result = this.createEditModelAndView(registroOfertaForm, null);
		} else {
			try {
				this.ofertaService.registrarOferta(registroOfertaForm);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(registroOfertaForm, "actor.commit.error");
			}
		}

		return result;

	}
	
//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
//	public ModelAndView save(@Valid final OfertaForm ofertaForm, final BindingResult bindingResult) {
//
//		ModelAndView result;
//		Actor alumno;
//
//		if (ofertaForm.getId() != 0) {
//			Assert.isTrue(ofertaForm.getId() == this.alumnoService.findByPrincipal().getId());
//		}
//
//		try {
//			Assert.isTrue(ofertaForm.getPassword().equals(ofertaForm.getPassword2()));
//		} catch (final Throwable oops) {
//			result = this.createEditModelAndView(ofertaForm, "actor.password.error");
//			return result;
//		}
//
//		if (bindingResult.hasErrors()) {
//			result = this.createEditModelAndView(ofertaForm);
//		} else {
//			try {
//				alumno = this.alumnoService.reconstruct(ofertaForm);
//				this.alumnoService.save(alumno);
//				result = new ModelAndView("redirect:/welcome/index.do");
//			} catch (final Throwable oops) {
//				result = this.createEditModelAndView(ofertaForm, "actor.commit.error");
//			}
//		}
//
//		return result;
//
//	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final OfertaForm ofertaForm) {
		ModelAndView result;

		result = this.createEditModelAndView(ofertaForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final OfertaForm ofertaForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("alumno/edit");
		result.addObject("ofertaForm", ofertaForm);
		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView createEditModelAndView(final RegistroOfertaForm registroOfertaForm, final String message) {
		ModelAndView result;
		Collection<Actor> tutores = new ArrayList<>();
		Collection<Actor> alumnos = new ArrayList<>();
		
		tutores = tutorService.findAll();
		alumnos = alumnoService.findAll();

		result = new ModelAndView("oferta/create");
		result.addObject("registroOfertaForm", registroOfertaForm);
		result.addObject("tutores", tutores);
		result.addObject("alumnos", alumnos);
		result.addObject("message", message);

		return result;
	}

}
