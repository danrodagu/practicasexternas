package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Oferta;
import domain.Valoracion;
import forms.ValoracionForm;
import services.ActorService;
import services.AlumnoService;
import services.OfertaService;
import services.TutorService;
import services.ValoracionService;

@Controller
@RequestMapping("/valoracion")
public class ValoracionController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AlumnoService alumnoService;
	
	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ValoracionService valoracionService;
	
	@Autowired
	private OfertaService ofertaService;

	// Constructors -----------------------------------------------------------

	public ValoracionController() {
		super();
	}		
	

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
	public ModelAndView create(@RequestParam(required = true) final int ofertaId, final HttpServletRequest request) {
		ModelAndView result;
		ValoracionForm valoracionForm;		

		valoracionForm = new ValoracionForm();
		valoracionForm.setIdOferta(ofertaId);

		result = this.createEditModelAndView(valoracionForm, null);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) final int valoracionId) {
		ModelAndView result;
		Valoracion valoracion;
		ValoracionForm valoracionForm;

		valoracion = this.valoracionService.findOne(valoracionId);
		Assert.notNull(valoracion);

		valoracionForm = this.valoracionService.takeForm(valoracion);
		result = this.createEditModelAndView(valoracionForm, null);

		return result;
	}

	// Save -------------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ValoracionForm valoracionForm, final BindingResult bindingResult) {

		ModelAndView result;
		Valoracion valoracion;	

		if (bindingResult.hasErrors()) {
			result = this.createEditModelAndView(valoracionForm, null);
		} else {
			try {
				valoracion = this.valoracionService.reconstruct(valoracionForm);
				valoracion = this.valoracionService.save(valoracion);
				
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(valoracionForm, "actor.commit.error");
			}
		}

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ValoracionForm valoracionForm, final String message) {
		ModelAndView result;
		Oferta oferta;
		
		oferta = ofertaService.findOne(valoracionForm.getIdOferta());

		result = new ModelAndView("valoracion/edit");
		result.addObject("valoracionForm", valoracionForm);
		result.addObject("oferta", oferta);
		result.addObject("message", message);

		return result;
	}	


}
