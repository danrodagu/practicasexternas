/* WelcomeController.java

 * 
 */

package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") String name,
			HttpServletRequest request) throws ServletException, IOException {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;

		HttpSession session = request.getSession();
		session.setAttribute("active", "welcome/index.do");

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);

		return result;
	}

	// Noticias ------------------------------------------------------------------

	@RequestMapping(value = "/noticias")
	public ModelAndView noticias(String name, HttpServletRequest request) throws ServletException, IOException {
		ModelAndView result;

		HttpSession session = request.getSession();
		session.setAttribute("active", "welcome/noticias.do");

		result = new ModelAndView("welcome/noticias");

		return result;
	}
}