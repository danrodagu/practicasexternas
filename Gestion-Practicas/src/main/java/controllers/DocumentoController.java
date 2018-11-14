package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Documento;
import forms.DocumentoForm;
import services.ActorService;
import services.DocumentoService;

@Controller
@RequestMapping("/documento")
public class DocumentoController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public DocumentoController() {
		super();
	}
	
	// Listing --------------------------------------
		@RequestMapping(value = "/list")
		public ModelAndView list(@RequestParam (required = false) final String alumnoId, final HttpServletRequest request) {
			ModelAndView result;
			Collection<Documento> documentos;
			Actor alumno;
			
			HttpSession session = request.getSession();
			session.setAttribute("active", "documentos");	
			
			alumno = actorService.findByPrincipal();
			
			documentos = documentoService.findDocumentosByActor(alumno.getId());				
			
			result = new ModelAndView("documento/list");

			result.addObject("documentos", documentos);

			return result;
		}	
	
	// Upload ---------------------------------------------------------------

		@RequestMapping(value = "/upload", method = RequestMethod.GET)
		public ModelAndView upload(final HttpServletRequest request) {
			ModelAndView result;
			DocumentoForm documentoForm;

//			HttpSession session = request.getSession();
//			session.setAttribute("active", "alumnos");

			documentoForm = new DocumentoForm();

			result = this.createEditModelAndView(documentoForm);

			return result;
		}

		// Save -------------------------------------------------------------------

		@RequestMapping(value = "/upload", method = RequestMethod.POST, params = "save")
		public ModelAndView upload(@Valid final DocumentoForm documentoForm, final BindingResult bindingResult) {
			ModelAndView result;
			Documento documento;

			if (bindingResult.hasErrors()) {
				result = this.createEditModelAndView(documentoForm);
			} else {
				try {
					documento = this.documentoService.reconstruct(documentoForm);
					this.documentoService.save(documento);
					result = new ModelAndView("redirect:/welcome/index.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(documentoForm, "actor.commit.error");
				}
			}

			return result;

		}

		// Ancillary methods ------------------------------------------------------

		protected ModelAndView createEditModelAndView(final DocumentoForm documentoForm) {
			ModelAndView result;

			result = this.createEditModelAndView(documentoForm, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(final DocumentoForm documentoForm, final String message) {
			ModelAndView result;

			result = new ModelAndView("documento/upload");
			result.addObject("documentoForm", documentoForm);
			result.addObject("message", message);

			return result;
		}

}
