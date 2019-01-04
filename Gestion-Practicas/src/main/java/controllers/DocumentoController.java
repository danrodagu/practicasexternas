package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Documento;
import domain.Oferta;
import forms.DocumentoForm;
import services.ActorService;
import services.DocumentoService;
import services.OfertaService;

@Controller
@RequestMapping("/documento")
public class DocumentoController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private OfertaService ofertaService;

	// Constructors -----------------------------------------------------------

	public DocumentoController() {
		super();
	}
	
	// Listing --------------------------------------
		@RequestMapping(value = "/list")
		public ModelAndView list(@RequestParam (required = true) final int ofertaId, final HttpServletRequest request) {
			ModelAndView result;
			Collection<Documento> documentos;
			
			documentos = documentoService.findDocumentosByOferta(ofertaId);				
			
			result = new ModelAndView("documento/list");

			result.addObject("documentos", documentos);
			result.addObject("ofertaId", ofertaId);
			
			return result;
		}	


		// Save -------------------------------------------------------------------



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
