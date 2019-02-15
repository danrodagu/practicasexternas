package controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Oferta;
import forms.AlumnoForm;
import forms.BusquedaAlumnosForm;
import services.ActorService;
import services.AlumnoService;
import services.CarpetaService;
import services.OfertaService;
import services.TutorService;

@Controller
@RequestMapping("/alumno")
public class AlumnoController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AlumnoService alumnoService;
	
	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private CarpetaService carpetaService;
	
	@Autowired
	private OfertaService ofertaService;

	// Constructors -----------------------------------------------------------

	public AlumnoController() {
		super();
	}
	
	
	// Listing --------------------------------------
	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam(required = true,  defaultValue = "0") final int listAll, @Valid final BusquedaAlumnosForm busqForm, final HttpServletRequest request) {
		ModelAndView result;
		Collection<Actor> alumnos;
		
		HttpSession session = request.getSession();
		
//		if(listAll == 0) {
//			session.setAttribute("active", "alumnos");
//			
//			Assert.isTrue(actorService.isTutor() || actorService.isCoordinador());			
//			alumnos = this.tutorService.findMyStudents();
//		}else {
//			session.setAttribute("active", "todosAlum");
//			
//			Assert.isTrue(actorService.isAdministrativo() || actorService.isCoordinador());
////			alumnos = alumnoService.findAll();
//			alumnos = alumnoService.alumnosFiltrados(busqForm, listAll);
//		}

		alumnos = alumnoService.alumnosFiltrados(busqForm, listAll);
		
		result = new ModelAndView("alumno/list");

		result.addObject("alumnos", alumnos);
		result.addObject("listAllAlum", listAll);

		return result;
	}
	
	@RequestMapping(value = "/practicas")
	public ModelAndView practicas(@RequestParam(required = true, defaultValue = "0") final int listAllAlum, @RequestParam(required = true, defaultValue = "0") final int alumnoId, final HttpServletRequest request) {
		ModelAndView result;
		Collection<Oferta> ofertas;
		Actor alumno;
		
		HttpSession session = request.getSession();
		session.setAttribute("active", "practicas");
		
		if (alumnoId == 0) {
			alumno = this.alumnoService.findByPrincipal();
			ofertas = ofertaService.ofertasByAlumno(alumno.getId());
		} else {
			alumno = this.alumnoService.findOne(alumnoId);
			
			if(listAllAlum == 0) { //solo accederan coordinador y tutores a este punto (lista de mis alumnos tutorados)
				ofertas = ofertaService.ofertasByAlumnoTutor(alumno.getId(), actorService.findByPrincipal().getId());
			}else { //solo accederan coordinador y administrativo a este punto (lista de todos los alumnos)
				ofertas = ofertaService.ofertasByAlumno(alumno.getId());
			}
		}

		
		
		result = new ModelAndView("oferta/list");

		result.addObject("ofertas", ofertas);

		return result;
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true, defaultValue = "0") int alumnoId) {
		ModelAndView result;
		Actor alumno;

		if (alumnoId == 0) {
			alumnoId = this.alumnoService.findByPrincipal().getId();
			alumno = this.alumnoService.findOne(alumnoId);
		} else {
			alumno = this.alumnoService.findOne(alumnoId);
		}

		Assert.notNull(alumno);

		result = new ModelAndView("alumno/display");

		result.addObject("alumno", alumno);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final HttpServletRequest request) {
		ModelAndView result;
		AlumnoForm alumnoForm;

		HttpSession session = request.getSession();
		session.setAttribute("active", "alta");

		alumnoForm = new AlumnoForm();

		result = this.createEditModelAndView(alumnoForm);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Actor alumno;
		AlumnoForm alumnoForm;

		alumno = this.alumnoService.findByPrincipal();
		Assert.notNull(alumno);

		alumnoForm = this.alumnoService.takeForm(alumno);
		result = this.createEditModelAndView(alumnoForm);

		return result;
	}

	// Save -------------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AlumnoForm alumnoForm, final BindingResult bindingResult) {

		ModelAndView result;
		Actor alumno;

		if (alumnoForm.getId() != 0) {
			Assert.isTrue(alumnoForm.getId() == this.alumnoService.findByPrincipal().getId());
		}

		try {
			Assert.isTrue(alumnoForm.getPassword().equals(alumnoForm.getPassword2()));
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(alumnoForm, "actor.password.error");
			return result;
		}

		if (bindingResult.hasErrors()) {
			result = this.createEditModelAndView(alumnoForm);
		} else {
			try {
				alumno = this.alumnoService.reconstruct(alumnoForm);
				alumno = this.alumnoService.save(alumno);
				if (alumnoForm.getId() == 0) {
					this.carpetaService.carpetasPorDefecto(alumno);
				}
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(alumnoForm, "actor.commit.error");
			}
		}

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final AlumnoForm alumnoForm) {
		ModelAndView result;

		result = this.createEditModelAndView(alumnoForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final AlumnoForm alumnoForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("alumno/edit");
		result.addObject("alumnoForm", alumnoForm);
		result.addObject("message", message);

		return result;
	}	


}
