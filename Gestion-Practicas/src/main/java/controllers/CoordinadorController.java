package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import services.CarpetaService;
import services.CoordinadorService;

@Controller
@RequestMapping("/coordinador")
public class CoordinadorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CoordinadorService coordinadorService;
	
	@Autowired
	private CarpetaService carpetaService;

	// Constructors -----------------------------------------------------------

	public CoordinadorController() {
		super();
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true, defaultValue = "0") int coordinadorId) {
		ModelAndView result;
		Actor coordinador;

		if (coordinadorId == 0) {
			coordinadorId = this.coordinadorService.findByPrincipal().getId();
			coordinador = this.coordinadorService.findOne(coordinadorId);
		} else {
			coordinador = this.coordinadorService.findOne(coordinadorId);
		}

		Assert.notNull(coordinador);

		result = new ModelAndView("coordinador/display");

		result.addObject("coordinador", coordinador);

		return result;
	}

}
