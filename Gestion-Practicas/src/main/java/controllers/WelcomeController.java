/* WelcomeController.java

 * 
 */

package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	ActorService actorService;

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false) final String error, HttpServletRequest request)
			throws ServletException, IOException {
		ModelAndView result;

		HttpSession session = request.getSession();
		session.setAttribute("active", "welcome/index.do");

		if (!this.actorService.isAnonymous()) {
			String uvus = this.actorService.findByPrincipal().getUserAccount().getUsername();
			String rol = this.actorService.findByPrincipal().getUserAccount().getAuthorities().iterator().next()
					.getAuthority().toLowerCase();
			session.setAttribute("uvus", uvus);
			session.setAttribute("rol", rol);
		}

		result = new ModelAndView("welcome/index");
		result.addObject("message", error);

		return result;

	}

	// Noticias ------------------------------------------------------------------

	@RequestMapping(value = "/noticias")
	public ModelAndView noticias(HttpServletRequest request) throws ServletException, IOException {
		ModelAndView result;

		HttpSession session = request.getSession();
		session.setAttribute("active", "welcome/noticias.do");

		result = new ModelAndView("welcome/noticias");

		return result;
	}
}