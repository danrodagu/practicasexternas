package controllers;

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
import domain.Token;
import forms.NuevoCoordiForm1;
import forms.NuevoCoordiForm2;
import forms.PeticionCambioCoordiForm;
import repositories.TokenRepository;
import services.ActorService;
import services.CoordinadorService;
import services.UtilService;

@Controller
@RequestMapping("/coordinador")
public class CoordinadorController extends AbstractController {

	@Autowired
	private TokenRepository tokenRepository;
	
	// Services ---------------------------------------------------------------

	@Autowired
	private CoordinadorService coordinadorService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UtilService utilService;

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
	
	@RequestMapping(value = "/peticionCambio", method = RequestMethod.GET)
	public ModelAndView peticionCambio() {
		ModelAndView result;
		PeticionCambioCoordiForm form;		
		
		form = new PeticionCambioCoordiForm();		

		result = new ModelAndView("coordinador/peticionCambio");
		result.addObject("form", form);
		
		return result;
	}
	
	@RequestMapping(value = "/peticionCambio", method = RequestMethod.POST, params = "save")
	public ModelAndView peticionCambio(@Valid final PeticionCambioCoordiForm form, final BindingResult bindingResult) {
		ModelAndView result;
		Token token;
		

		if (bindingResult.hasErrors()) {
			result = this.createPeticionCambioModelAndView(form, null);
		} else {		
			try {
				token = new Token();
				actorService.enviarFormCambioCoordiCorreo(form.getEmail(), token);
				
				result = new ModelAndView("welcome/index");
				result.addObject("message", "coordinador.peticionCambio.success");
			} catch (final Throwable oops) {
				result = this.createPeticionCambioModelAndView(form, "actor.commit.error");
			}
		}

		return result;

	}
	
	@RequestMapping(value = "/nuevoCoordinador", method = RequestMethod.GET)
	public ModelAndView nuevoCoordinador(@RequestParam(required = false) final String confirmationToken, @RequestParam(required = false) final int fase) {
		ModelAndView result;
		NuevoCoordiForm1 nuevoCoordiForm1;
		NuevoCoordiForm2 nuevoCoordiForm2;
		Token token;		
				
		token = tokenRepository.findTokenByConfirmationToken(confirmationToken);		
		
		if(!utilService.tokenExpirado(token)) {
			result = new ModelAndView("coordinador/nuevoCoordinador");
			result.addObject("faseForm", fase);
			
			if(fase == 1) {
				nuevoCoordiForm1 = new NuevoCoordiForm1();
				nuevoCoordiForm1.setConfirmationToken(confirmationToken);
				result.addObject("nuevoCoordiForm", nuevoCoordiForm1);			
			}else if (fase == 2) {
				nuevoCoordiForm2 = new NuevoCoordiForm2();
				nuevoCoordiForm2.setConfirmationToken(confirmationToken);
				result.addObject("nuevoCoordiForm", nuevoCoordiForm2);
			}			
			
		}else {
			result = new ModelAndView("welcome/index");
			result.addObject("message", "coordinador.tokenExpirado.error");
		}		
		
		return result;
	}
	
	@RequestMapping(value = "/nuevoCoordinador", method = RequestMethod.POST, params = "save")
	public ModelAndView nuevoCoordinador(@Valid final NuevoCoordiForm1 nuevoCoordiForm1, @Valid final NuevoCoordiForm2 nuevoCoordiForm2, final BindingResult bindingResult) {
		ModelAndView result;
		Token token;
		
		if(nuevoCoordiForm1 != null) {
			if (bindingResult.hasErrors()) {
				result = new ModelAndView("coordinador/nuevoCoordinador");
				result.addObject("nuevoCoordiForm", nuevoCoordiForm1);
				result.addObject("faseForm", 1);
			}else {
				Actor actor = actorService.findByUsername(nuevoCoordiForm1.getUvus());
				if(actor != null) {
					//se ejecuta el metodo de registro (cambiar authorities) sin necesidad del 2do form
				}
			}
		}
		
		if(nuevoCoordiForm2 != null) {
			if (bindingResult.hasErrors()) {
				result = new ModelAndView("coordinador/nuevoCoordinador");
				result.addObject("nuevoCoordiForm", nuevoCoordiForm2);
				result.addObject("faseForm", 2);
			}else {
				//se ejecuta el metodo de registro con el 2do form y authorities
			}
		}

//		if (bindingResult.hasErrors()) {
//			result = this.createPeticionCambioModelAndView(form, null);
//		} else {		
//			try {
//				token = new Token();
//				actorService.enviarFormCambioCoordiCorreo(form.getEmail(), token);
//				
//				result = new ModelAndView("welcome/index");
//				result.addObject("message", "coordinador.peticionCambio.success");
//			} catch (final Throwable oops) {
//				result = this.createPeticionCambioModelAndView(form, "actor.commit.error");
//			}
//		}

		return result;

	}
	
	protected ModelAndView createPeticionCambioModelAndView(final PeticionCambioCoordiForm form, final String message) {
		ModelAndView result;
		
		result = new ModelAndView("coordinador/peticionCambio");
		
		result.addObject("form", form);
		result.addObject("message", message);

		return result;
	}
	
	

}
