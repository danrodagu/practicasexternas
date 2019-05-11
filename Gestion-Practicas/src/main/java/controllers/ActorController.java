package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import forms.EdicionPerfilForm;
import forms.RecoverPasswordForm;
import services.ActorService;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public ActorController() {
		super();
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true, defaultValue = "0") int actorId) {
		ModelAndView result;
		Actor actor;

		if (actorId == 0) {
			actorId = actorService.findByPrincipal().getId();
			actor = actorService.findOne(actorId);
		} else {
			actor = actorService.findOne(actorId);
		}

		Assert.notNull(actor);

		result = new ModelAndView("actor/display");

		result.addObject("actor", actor);

		return result;
	}
	
	@RequestMapping(value = "/recoverPassword", method = RequestMethod.GET)
	public ModelAndView recoverPassword() {
		ModelAndView result;
		
		result = new ModelAndView("actor/recoverPassword");		
		result.addObject("recoverPasswordForm", new RecoverPasswordForm());

		return result;
	}
	
	@RequestMapping(value = "/recoverPassword", method = RequestMethod.POST, params = "save")
	public ModelAndView recoverPassword(@Valid final RecoverPasswordForm recoverPasswordForm, final BindingResult bindingResult, final HttpServletRequest request, final HttpServletResponse response) {
		ModelAndView result;
		Actor actor;
		String email = "";				

		if (bindingResult.hasErrors()) {
			result = new ModelAndView("actor/recoverPassword");			
		} else {			
			try {				
				actor = actorService.findByUsername(recoverPasswordForm.getUsername());
				
				if(actor != null) {
					email = actor.getEmail();
				}			
				
				if(StringUtils.isEmpty(email)) {
					result = new ModelAndView("actor/recoverPassword");
					result.addObject("message", "actor.recover.password.error");
				}else {
					result = new ModelAndView("welcome/index");
					
					String newPassword = actorService.generateSecureRandomPassword();
					actorService.changePassword(actor, newPassword);
					
					actorService.enviarCredencialesCorreo(email, actor.getUserAccount().getUsername(), newPassword, true, request);
					
					result.addObject("message", "actor.recover.password.success");
				}			
			} catch (final Throwable oops) {
				result = new ModelAndView("welcome/index");
				result.addObject("message", "actor.internal.error");
				return result;
			}
						
		}

		return result;
	}
	
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false, defaultValue = "0") final int actorId) {
		ModelAndView result;
		Actor actorLogueado;
		Actor actorPerfil;
		boolean mismoActorLogYPerfil;
		EdicionPerfilForm edicionPerfilForm;

		actorLogueado = this.actorService.findByPrincipal();
		
		if (actorId == 0) {
			actorPerfil = this.actorService.findByPrincipal();
		}else {
			actorPerfil = this.actorService.findOne(actorId);
		}
		
		Assert.notNull(actorPerfil);

		edicionPerfilForm = this.actorService.takeForm(actorPerfil);
		result = this.createEditModelAndView(edicionPerfilForm);
		
		if(actorLogueado.getId() == actorPerfil.getId()) {
			mismoActorLogYPerfil = true;
		}else {
			mismoActorLogYPerfil = false;
		}
		
		result.addObject("rolLogueado", actorLogueado.getUserAccount().getAuthorities().iterator().next().getAuthority());
		result.addObject("rolPerfil", actorPerfil.getUserAccount().getAuthorities().iterator().next().getAuthority());
		result.addObject("mismoActorLogYPerfil", mismoActorLogYPerfil);

		return result;
	}

	// Save -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EdicionPerfilForm edicionPerfilForm, final BindingResult bindingResult) {

		ModelAndView result;
		Actor actor;

		if (StringUtils.isNotBlank(edicionPerfilForm.getPassword()) && StringUtils.isNotBlank(edicionPerfilForm.getPassword2())) {			
			try {
				Assert.isTrue(edicionPerfilForm.getPassword().equals(edicionPerfilForm.getPassword2()));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(edicionPerfilForm, "actor.password.error");
				return result;
			}
			try {
				Assert.isTrue(edicionPerfilForm.getPassword().length() >= 5 && edicionPerfilForm.getPassword().length() <= 32);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(edicionPerfilForm, "actor.password.longitud.error");
				return result;
			}
		}
		

		if (bindingResult.hasErrors()) {
			result = this.createEditModelAndView(edicionPerfilForm);
		} else {
			try {
				actor = this.actorService.reconstruct(edicionPerfilForm);
				actor = this.actorService.save(actor);				
				result = new ModelAndView("redirect:/actor/edit.do?message=actor.modificacion.success");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(edicionPerfilForm, "actor.unique.error");
			}
		}

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EdicionPerfilForm edicionPerfilForm) {
		ModelAndView result;

		result = this.createEditModelAndView(edicionPerfilForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EdicionPerfilForm edicionPerfilForm, final String message) {
		ModelAndView result;
		Actor actorLogueado;
		Actor actorPerfil;
		boolean mismoActorLogYPerfil;

		actorLogueado = this.actorService.findByPrincipal();		
		actorPerfil = this.actorService.findOne(edicionPerfilForm.getId());
		
		if(actorLogueado.getId() == actorPerfil.getId()) {
			mismoActorLogYPerfil = true;
		}else {
			mismoActorLogYPerfil = false;
		}		
		

		result = new ModelAndView("actor/edit");
		result.addObject("edicionPerfilForm", edicionPerfilForm);
		result.addObject("message", message);
		
		result.addObject("rolLogueado", actorLogueado.getUserAccount().getAuthorities().iterator().next().getAuthority());
		result.addObject("rolPerfil", actorPerfil.getUserAccount().getAuthorities().iterator().next().getAuthority());
		result.addObject("mismoActorLogYPerfil", mismoActorLogYPerfil);

		return result;
	}
	
	@RequestMapping(value = "/desactivar", method = RequestMethod.GET)
	public ModelAndView desactivar(@RequestParam(required = true) final int actorId, @RequestHeader(value = "referer", required = false) final String referer) {
		ModelAndView result;		

		try {			
			actorService.desactivarUsuario(actorId);
			result = new ModelAndView("redirect:" + referer);
		} catch (Throwable oops) {
			result = new ModelAndView("welcome/index");
			result.addObject("message", "actor.commit.error");
		}		

		return result;
	}
	
	@RequestMapping(value = "/activar", method = RequestMethod.GET)
	public ModelAndView activar(@RequestParam(required = true) final int actorId, @RequestHeader(value = "referer", required = false) final String referer) {
		ModelAndView result;

		try {			
			actorService.activarUsuario(actorId);
			result = new ModelAndView("redirect:" + referer);
		} catch (Throwable oops) {
			result = new ModelAndView("welcome/index");
			result.addObject("message", "actor.commit.error");
		}		

		return result;
	}


}
