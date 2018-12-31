package controllers;

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
import forms.TutorForm;
import services.CarpetaService;
import services.TutorService;

@Controller
@RequestMapping("/tutor")
public class TutorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private CarpetaService carpetaService;

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
	
	// Creation ---------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(final HttpServletRequest request) {
			ModelAndView result;
			TutorForm tutorForm;

			HttpSession session = request.getSession();
			session.setAttribute("active", "alta");

			tutorForm = new TutorForm();

			result = this.createEditModelAndView(tutorForm);

			return result;
		}

		// Edition ----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit() {
			ModelAndView result;
			Actor tutor;
			TutorForm tutorForm;

			tutor = this.tutorService.findByPrincipal();
			Assert.notNull(tutor);

			tutorForm = this.tutorService.takeForm(tutor);
			result = this.createEditModelAndView(tutorForm);

			return result;
		}

		// Save -------------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid final TutorForm tutorForm, final BindingResult bindingResult) {

			ModelAndView result;
			Actor tutor;

			if (tutorForm.getId() != 0) {
				Assert.isTrue(tutorForm.getId() == this.tutorService.findByPrincipal().getId());
			}

			try {
				Assert.isTrue(tutorForm.getPassword().equals(tutorForm.getPassword2()));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tutorForm, "actor.password.error");
				return result;
			}

			if (bindingResult.hasErrors()) {
				result = this.createEditModelAndView(tutorForm);
			} else {
				try {
					tutor = this.tutorService.reconstruct(tutorForm);
					tutor = this.tutorService.save(tutor);
					if (tutorForm.getId() == 0) {
						this.carpetaService.carpetasPorDefecto(tutor);
					}
					result = new ModelAndView("redirect:/welcome/index.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(tutorForm, "actor.commit.error");
				}
			}

			return result;

		}

		// Ancillary methods ------------------------------------------------------

		protected ModelAndView createEditModelAndView(final TutorForm tutorForm) {
			ModelAndView result;

			result = this.createEditModelAndView(tutorForm, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(final TutorForm tutorForm, final String message) {
			ModelAndView result;

			result = new ModelAndView("tutor/edit");
			result.addObject("tutorForm", tutorForm);
			result.addObject("message", message);

			return result;
		}

}
