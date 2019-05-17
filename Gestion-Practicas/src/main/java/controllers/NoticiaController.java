
package controllers;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Noticia;
import forms.NoticiaForm;
import services.NoticiaService;

@RequestMapping("/noticia")
@Controller
public class NoticiaController {
	
	@PersistenceContext( unitName="Gestion-Practicas" )
	private EntityManager em;

	// Repositories ---------------------------------------------------------------
	
	// Services ---------------------------------------------------------------
	@Autowired
	private NoticiaService	noticiaService;

	// Constructors -------------------------------------------------------
	public NoticiaController() {
		super();
	}

	// Listing --------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final HttpServletRequest request) {
		ModelAndView result;
		Collection<Noticia> noticias;
		
		HttpSession session = request.getSession();
		session.setAttribute("active", "noticias");

		noticias = this.noticiaService.findAll();
		result = new ModelAndView("noticia/list");

		result.addObject("noticias", noticias);

		return result;
	}

	// Display -----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) final int noticiaId) {
		ModelAndView result;
		Noticia noticia;

		noticia = this.noticiaService.findOne(noticiaId);
		
		result = new ModelAndView("noticia/display");

		result.addObject("noticia", noticia);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		NoticiaForm noticiaForm;

		noticiaForm = new NoticiaForm();

		result = this.createEditModelAndView(noticiaForm);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) final int noticiaId) {
		ModelAndView result;
		Noticia noticia;
		NoticiaForm noticiaForm;

		noticia = noticiaService.findOne(noticiaId);
		
		noticiaForm = noticiaService.takeForm(noticia);

		result = this.createEditModelAndView(noticiaForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final NoticiaForm noticiaForm, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		Noticia noticia;

		boolean cuerpoVacio = false;		
		String cuerpo = (String) request.getSession().getAttribute("cuerpoNoticia");
		request.removeAttribute("cuerpoNoticia");
		
		if(cuerpo == null || cuerpo.isEmpty() || cuerpo.equals("<p><br></p>") || cuerpo.equals("<br>")) {
			cuerpoVacio = true;
		}
		
		if (binding.hasErrors() || cuerpoVacio) {
			result = this.createEditModelAndView(noticiaForm);
			if(cuerpoVacio) {
				result.addObject("validaCuerpo", "noticia.cuerpo.validacion");
			}
		} else {
			try {
				noticiaForm.setCuerpo(cuerpo);
				noticia = this.noticiaService.reconstruct(noticiaForm);
				this.noticiaService.save(noticia);
				result = new ModelAndView("redirect:/noticia/list.do?message=noticia.success");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(noticiaForm, "noticia.commit.error");
			}
		}

		return result;
	}
	
	

	@RequestMapping(value = "/noticiaAjax", method = RequestMethod.POST)
	public ResponseEntity<Object> noticiaAjax(@RequestBody final String cuerpo, final HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		String body = null;
		String error = null;
		
		// se usa para obtener solo el cuerpo del noticia
		body = cuerpo.substring(11, cuerpo.length()-2);
        
        if(body == null
        	|| (!body.startsWith("<p>") && !body.startsWith("<p") && !body.startsWith("<ul>") && !body.startsWith("<ol>"))
        	|| (!body.endsWith("</p>") && !body.endsWith("</ul>") && !body.endsWith("</ol>"))) {
        	error = "1";
        }else {
//        	body = body.replaceAll("/\\<\\/p>(?=.*\\<\\/p>/g)", "");
        	String aux = body;
        	aux = aux.replaceAll("</p><p><br></p>", "<br><br>");
        	aux = aux.replaceAll("</p><p>", "<br>");
        	aux = aux.replaceAll("<p>", "");
        	aux = aux.replaceAll("</p>", "");
        	//Para escapar las dobles comillas
        	aux = aux.replaceAll("\\\\\"", "\"");
        	//Para escapar la barra inversa '\'
        	aux = aux.replaceAll("\\\\+", "\\\\");
//        	aux = "<p>" + aux + "</p>";
        	body = aux;
        	
        	request.getSession().setAttribute("cuerpoNoticia", body);
        }		
		
		return new ResponseEntity<Object>(error, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = true) final int noticiaId) {
		ModelAndView result;
		Noticia noticia;

		noticia = this.noticiaService.findOne(noticiaId);

		this.noticiaService.delete(noticia);
		
		result = new ModelAndView("redirect:/noticia/list.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final NoticiaForm noticiaForm) {
		ModelAndView result;

		result = this.createEditModelAndView(noticiaForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final NoticiaForm noticiaForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("noticia/edit");
		result.addObject("noticiaForm", noticiaForm);
		result.addObject("message", message);

		return result;
	}

}
