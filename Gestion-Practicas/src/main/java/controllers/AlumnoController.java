package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import services.AlumnoService;

@Controller
@RequestMapping("/alumno")
public class AlumnoController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AlumnoService alumnoService;

	// Constructors -----------------------------------------------------------

	public AlumnoController() {
		super();
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

}
