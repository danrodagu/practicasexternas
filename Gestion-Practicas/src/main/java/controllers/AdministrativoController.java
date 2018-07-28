package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import services.AdministrativoService;

@Controller
@RequestMapping("/administrativo")
public class AdministrativoController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AdministrativoService administrativoService;

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

}
