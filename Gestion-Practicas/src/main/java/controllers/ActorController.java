package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;

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

}
