
package controllers.actor;

import java.util.ArrayList;
import java.util.Collection;

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
import domain.Mensaje;
import forms.MensajeForm;
import services.ActorService;
import services.MensajeService;

@Controller
@RequestMapping("/mensaje")
public class MensajeController {

	// Services ---------------------------------------------------------------
	@Autowired
	private MensajeService	mensajeService;

	@Autowired
	private ActorService	actorService;


	// Constructors -------------------------------------------------------
	public MensajeController() {
		super();
	}

	// Listing --------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int carpetaId) {
		ModelAndView result;
		Collection<Mensaje> mensajes;

		mensajes = this.mensajeService.findMensajesByCarpeta(carpetaId);
		result = new ModelAndView("mensaje/list");

		result.addObject("mensajes", mensajes);
		result.addObject("requestURI", "mensaje/list.do?carpetaId=" + carpetaId);

		return result;
	}

	// Display -----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) final int mensajeId) {
		ModelAndView result;
		Mensaje mensaje;

		mensaje = this.mensajeService.findOne(mensajeId);
		Assert.isTrue((mensaje.getReceptor().getId() == this.actorService.findByPrincipal().getId()) || (mensaje.getEmisor().getId() == this.actorService.findByPrincipal().getId()));

		result = new ModelAndView("mensaje/display");

		result.addObject("mensaje", mensaje);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MensajeForm mensajeForm;

		mensajeForm = new MensajeForm();

		result = this.createEditModelAndView(mensajeForm);

		return result;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam(required = true) final int mensajeId, @RequestParam(required = true) final int actorId) {
		ModelAndView result;
		MensajeForm mensajeForm;
		Collection<Actor> actores = new ArrayList<Actor>();
		actores.add(this.actorService.findOne(actorId));

		mensajeForm = new MensajeForm();
		mensajeForm = this.mensajeService.responderMensaje(mensajeId);

		result = this.createEditModelAndView(mensajeForm);
		
		result.addObject("actores", actores);

		return result;
	}

	@RequestMapping(value = "/forward", method = RequestMethod.GET)
	public ModelAndView forward(@RequestParam(required = true) final int mensajeId) {
		ModelAndView result;
		MensajeForm mensajeForm;

		mensajeForm = new MensajeForm();
		mensajeForm = this.mensajeService.reenviarMensaje(mensajeId);

		result = this.createEditModelAndView(mensajeForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MensajeForm mensajeForm, final BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(mensajeForm);
		} else {
			try {
				this.mensajeService.createMensaje(mensajeForm);
				result = new ModelAndView("redirect:/carpeta/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(mensajeForm, "mensaje.commit.error");
			}
		}

		return result;
	}
	
	
	
//	@ResponseBody
	@RequestMapping(value = "/prueba", method = RequestMethod.POST)	
	public String prueba() {
		String b = "1";
		
		String a = null;

		return b;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = true) final int mensajeId) {
		ModelAndView result;
		MensajeForm mensajeForm;
		Mensaje mensaje;

		mensaje = this.mensajeService.findOne(mensajeId);

		try {
			this.mensajeService.delete(mensaje);
			result = new ModelAndView("redirect:/carpeta/list.do");
		} catch (final Throwable oops) {
			mensajeForm = this.mensajeService.createMensajeForm(mensaje.getId());
			result = this.createEditModelAndView(mensajeForm, "mensaje.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final MensajeForm mensajeForm) {
		ModelAndView result;

		result = this.createEditModelAndView(mensajeForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MensajeForm mensajeForm, final String message) {
		ModelAndView result;
		Collection<Actor> actores;
		actores = this.actorService.findAll();

		result = new ModelAndView("mensaje/create");
		result.addObject("mensajeForm", mensajeForm);
		result.addObject("message", message);
		result.addObject("actores", actores);

		return result;
	}

}
