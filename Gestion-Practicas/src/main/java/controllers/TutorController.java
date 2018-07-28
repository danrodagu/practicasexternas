package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import services.TutorService;

@Controller
@RequestMapping("/tutor")
public class TutorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TutorService tutorService;

	// Constructors -----------------------------------------------------------

	public TutorController() {
		super();
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true, defaultValue = "0") int tutorId) {
		ModelAndView result;
		Actor tutor;

		if (tutorId == 0) {
			tutorId = this.tutorService.findByPrincipal().getId();
			tutor = this.tutorService.findOne(tutorId);
		} else {
			tutor = this.tutorService.findOne(tutorId);
		}

		Assert.notNull(tutor);

		result = new ModelAndView("tutor/display");

		result.addObject("tutor", tutor);

		return result;
	}

}
