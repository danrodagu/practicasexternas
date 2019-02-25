package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
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
					
					actorService.enviarCredencialesCorreo(email, actor.getUserAccount().getUsername(), newPassword, true);
					
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


}
