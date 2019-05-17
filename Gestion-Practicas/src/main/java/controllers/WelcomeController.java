/* WelcomeController.java

 * 
 */

package controllers;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Documento;
import services.ActorService;
import services.DocumentoService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private DocumentoService documentoService;

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false) final String error, final HttpServletRequest request)
			throws ServletException, IOException {
		ModelAndView result;

		HttpSession session = request.getSession();
		session.setAttribute("active", "inicio");

		if (!this.actorService.isAnonymousPrincipal()) {
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

//	@RequestMapping(value = "/noticias")
//	public ModelAndView noticias(final HttpServletRequest request) throws ServletException, IOException {
//		ModelAndView result;
//
//		HttpSession session = request.getSession();
//		session.setAttribute("active", "noticias");
//
//		result = new ModelAndView("welcome/noticias");
//
//		return result;
//	}
	
	// Documentacion ------------------------------------------------------------------

	@RequestMapping(value = "/documentacion")
	public ModelAndView documentacion(final HttpServletRequest request) {
		ModelAndView result;
		Collection<Documento> documentos;
		Actor principal;
		boolean esCoordiAdmin;		

		HttpSession session = request.getSession();
		session.setAttribute("active", "documentacion");
		
		documentos = documentoService.findDocumentosSinOferta();
		
		esCoordiAdmin = false;
		
		if(!actorService.isAnonymousPrincipal()){
			principal = actorService.findByPrincipal();
			
			if((actorService.isCoordinador(principal.getId()) || actorService.isAdministrativo(principal.getId()))) {
				esCoordiAdmin = true;
			}
		}

		result = new ModelAndView("welcome/documentacion");
		
		result.addObject("documentos", documentos);
		result.addObject("esCoordiAdmin", esCoordiAdmin);

		return result;
	}
}