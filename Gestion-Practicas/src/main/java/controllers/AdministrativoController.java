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
import forms.AdministrativoForm;
import forms.BusquedaAdministrativosForm;
import services.AdministrativoService;
import services.CarpetaService;

@Controller
@RequestMapping("/administrativo")
public class AdministrativoController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AdministrativoService administrativoService;
	
	@Autowired
	private CarpetaService carpetaService;
	
	// Constructors -----------------------------------------------------------

	public AdministrativoController() {
		super();
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true, defaultValue = "0") int administrativoId) {
		ModelAndView result;
		Actor administrativo;

		if (administrativoId == 0) {
			administrativoId = this.administrativoService.findByPrincipal().getId();
			administrativo = this.administrativoService.findOne(administrativoId);
		} else {
			administrativo = this.administrativoService.findOne(administrativoId);
		}

		Assert.notNull(administrativo);

		result = new ModelAndView("administrativo/display");

		result.addObject("administrativo", administrativo);

		return result;
	}
	
	// Listing --------------------------------------
	@RequestMapping(value = "/list")
	public ModelAndView list(@Valid final BusquedaAdministrativosForm busqForm, final HttpServletRequest request) {
		ModelAndView result;
		Collection<Actor> administrativos;
		
		HttpSession session = request.getSession();
		session.setAttribute("active", "listado");

		administrativos = administrativoService.administrativosFiltrados(busqForm);
		
		result = new ModelAndView("administrativo/list");

		result.addObject("administrativos", administrativos);

		return result;
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final HttpServletRequest request) {
		ModelAndView result;
		AdministrativoForm administrativoForm;

		HttpSession session = request.getSession();
		session.setAttribute("active", "alta");

		administrativoForm = new AdministrativoForm();

		result = this.createEditModelAndView(administrativoForm);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Actor administrativo;
		AdministrativoForm administrativoForm;

		administrativo = this.administrativoService.findByPrincipal();
		Assert.notNull(administrativo);

		administrativoForm = this.administrativoService.takeForm(administrativo);
		result = this.createEditModelAndView(administrativoForm);

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AdministrativoForm administrativoForm, final BindingResult bindingResult, final HttpServletRequest request) {

		ModelAndView result;
		Actor administrativo;

		if (administrativoForm.getId() != 0) {
			Assert.isTrue(administrativoForm.getId() == this.administrativoService.findByPrincipal().getId());
			
			try {
				Assert.isTrue(administrativoForm.getPassword().equals(administrativoForm.getPassword2()));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrativoForm, "actor.password.error");
				return result;
			}
		}

		

		if (bindingResult.hasErrors()) {
			result = this.createEditModelAndView(administrativoForm);
		} else {
			try {
				administrativo = this.administrativoService.reconstruct(administrativoForm, request);
				administrativo = this.administrativoService.save(administrativo);
				if (administrativoForm.getId() == 0) {
					this.carpetaService.carpetasPorDefecto(administrativo);
				}
				result = new ModelAndView("welcome/index");
				result.addObject("message", "administrativo.creado.success");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrativoForm, "actor.commit.error");
			}
		}

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final AdministrativoForm administrativoForm) {
		ModelAndView result;

		result = this.createEditModelAndView(administrativoForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final AdministrativoForm administrativoForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("administrativo/edit");
		result.addObject("administrativoForm", administrativoForm);
		result.addObject("message", message);

		return result;
	}

}
