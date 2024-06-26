package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Carpeta;
import forms.CarpetaForm;
import services.ActorService;
import services.AlumnoService;
import services.CarpetaService;

@Controller
@RequestMapping("/carpeta")
public class CarpetaController extends AbstractController {

	// Services -------------------------------------
	@Autowired
	private CarpetaService carpetaService;

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private AlumnoService alumnoService;

	// Constructors ---------------------------------
	public CarpetaController() {
		super();
	}

	// Listing --------------------------------------
	@RequestMapping(value = "/list")
	public ModelAndView list(final HttpServletRequest request) {
		ModelAndView result;
		Collection<Carpeta> carpetas;
		
		HttpSession session = request.getSession();
		session.setAttribute("active", "mensajes");		

		carpetas = this.carpetaService.findCarpetaByActor(this.actorService
				.findByPrincipal().getId());
		result = new ModelAndView("carpeta/list");

		result.addObject("carpetas", carpetas);

		return result;
	}

	// Creation ------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CarpetaForm carpetaForm;

		carpetaForm = new CarpetaForm();

		result = this.createEditModelAndView(carpetaForm);

		return result;
	}

	// Edition ----------------------------------------------

//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
//	public ModelAndView edit(@RequestParam final int carpetaId) {
//		ModelAndView result;
//		CarpetaForm carpetaForm;
//		Actor a;
//		Carpeta f;
//
//		carpetaForm = this.carpetaService.copiarCarpeta(carpetaId);
//		a = this.actorService.findByPrincipal();
//		f = this.carpetaService.findOne(carpetaId);
//		result = this.createEditModelAndView(carpetaForm);
//
//		Assert.isTrue(f.getActor().getId() == a.getId());
//
//		return result;
//	}
//
//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
//	public ModelAndView save(@Valid final CarpetaForm carpetaForm,
//			final BindingResult binding, final RedirectAttributes redir) {
//		ModelAndView result;
//
//		if (binding.hasErrors()) {
//			result = this.createEditModelAndView(carpetaForm);
//		} else {
//			try {
//				Assert.isTrue(this.carpetaService.noExisteCarpeta(carpetaForm
//						.getNombre()));
//				this.carpetaService.reconstruct(carpetaForm);
//				result = new ModelAndView("redirect:/carpeta/list.do");
//				redir.addFlashAttribute("message", "carpeta.commit.ok");
//			} catch (final Throwable oops) {
//				result = this.createEditModelAndView(carpetaForm,
//						"carpeta.commit.error");
//			}
//		}
//
//		return result;
//	}
//
//	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
//	public ModelAndView delete(final CarpetaForm carpetaForm,
//			final BindingResult binding, final RedirectAttributes redir) {
//		ModelAndView result;
//
//		try {
//			this.carpetaService.deleteCarpeta(carpetaForm);
//			result = new ModelAndView("redirect:/carpeta/list.do");
//			redir.addFlashAttribute("message", "carpeta.commit.ok");
//		} catch (final Throwable oops) {
//			result = this.createEditModelAndView(carpetaForm,
//					"carpeta.commit.error");
//		}
//
//		return result;
//	}

	// Ancillary methods ------------------------------------

	protected ModelAndView createEditModelAndView(final CarpetaForm carpetaForm) {
		ModelAndView result;

		result = this.createEditModelAndView(carpetaForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CarpetaForm carpetaForm,
			final String message) {
		ModelAndView result;
		result = new ModelAndView("carpeta/edit");

		result.addObject("carpetaForm", carpetaForm);
		result.addObject("message", message);

		return result;
	}

}
