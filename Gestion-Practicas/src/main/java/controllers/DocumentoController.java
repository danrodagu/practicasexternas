package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Documento;
import domain.Oferta;
import forms.ActaForm;
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
			Oferta oferta;
			boolean esAlumno;
			
			oferta = ofertaService.findOne(ofertaId);
			
			documentos = documentoService.findDocumentosByOferta(ofertaId);				
			
			result = new ModelAndView("documento/list");
			
			esAlumno = actorService.isAlumno();

			result.addObject("documentos", documentos);
			result.addObject("oferta", oferta);
			result.addObject("esAlumno", esAlumno);
			
			return result;
		}	


		// Save -------------------------------------------------------------------

		

		// Ancillary methods ------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam(required = true) final int documentoId) {
			ModelAndView result;
			Documento documento;

			documento = this.documentoService.findOne(documentoId);
			
			result = new ModelAndView("redirect:/documento/list.do?ofertaId=" + documento.getOferta().getId());
			
			this.documentoService.delete(documento);					

			return result;
		}
		
		@RequestMapping(value = "/acta/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam(required = true) final int ofertaId, final HttpServletRequest request) {
			ModelAndView result;
			ActaForm actaForm;
			
			actaForm = new ActaForm();
			actaForm.setIdOferta(ofertaId);

			result = new ModelAndView("documento/acta/create");
			result.addObject("actaForm", actaForm);

			return result;
		}
		
		protected ModelAndView createEditModelAndView(final DocumentoForm documentoForm) {
			ModelAndView result;

			result = this.createEditModelAndView(documentoForm, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(final DocumentoForm documentoForm, final String message) {
			ModelAndView result;

			result = new ModelAndView("documento/edit");
			result.addObject("documentoForm", documentoForm);
			result.addObject("message", message);

			return result;
		}

}
