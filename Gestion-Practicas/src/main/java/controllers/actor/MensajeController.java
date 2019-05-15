
package controllers.actor;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Carpeta;
import domain.Mensaje;
import domain.Oferta;
import forms.MensajeForm;
import repositories.MensajeRepository;
import services.ActorService;
import services.CarpetaService;
import services.MensajeService;
import services.OfertaService;
import services.UtilService;

@RequestMapping("/mensaje")
@Controller
public class MensajeController {
	
	@PersistenceContext( unitName="Gestion-Practicas" )
	private EntityManager em;

	// Repositories ---------------------------------------------------------------
	@Autowired
	private MensajeRepository	mensajeRepository;
	
	// Services ---------------------------------------------------------------
	@Autowired
	private MensajeService	mensajeService;

	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private OfertaService	ofertaService;
	
	@Autowired
	private CarpetaService	carpetaService;
	
	@Autowired
	private UtilService	utilService;


	// Constructors -------------------------------------------------------
	public MensajeController() {
		super();
	}

	// Listing --------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int carpetaId) {
		ModelAndView result;
		Collection<Mensaje> mensajes;
		Carpeta carpeta;
		
		carpeta = this.carpetaService.findOne(carpetaId);
		mensajes = this.mensajeService.findMensajesByCarpeta(carpetaId);
		result = new ModelAndView("mensaje/list");

		result.addObject("mensajes", mensajes);
		result.addObject("carpeta", carpeta);
		result.addObject("requestURI", "mensaje/list.do?carpetaId=" + carpetaId);

		return result;
	}

	// Display -----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) final int mensajeId) {
		ModelAndView result;
		Mensaje mensaje;

		mensaje = this.mensajeService.findOne(mensajeId);
		Assert.isTrue((mensaje.getReceptor().getId() == this.actorService.findByPrincipal().getId()) || (mensaje.getEmisor().getId() == this.actorService.findByPrincipal().getId()));
		mensaje.setLeido(true);
		this.mensajeRepository.save(mensaje);
		
		result = new ModelAndView("mensaje/display");

		result.addObject("mensaje", mensaje);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false) final Integer actorId) {
		ModelAndView result;
		MensajeForm mensajeForm;

		mensajeForm = new MensajeForm();
		
		if(actorId != null) {
			mensajeForm.setIdReceptor(actorId);
		}

		result = this.createEditModelAndView(mensajeForm);

		return result;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam(required = true) final int mensajeId, @RequestParam(required = true) final int actorId) {
		ModelAndView result;
		MensajeForm mensajeForm;
		Collection<Actor> actores = new ArrayList<Actor>();
		actores.add(this.actorService.findOne(actorId));

		mensajeForm = new MensajeForm();
		mensajeForm = this.mensajeService.responderMensaje(mensajeId);

		result = this.createEditModelAndView(mensajeForm);
		
		result.addObject("actores", actores);

		return result;
	}

	@RequestMapping(value = "/forward", method = RequestMethod.GET)
	public ModelAndView forward(@RequestParam(required = true) final int mensajeId) {
		ModelAndView result;
		MensajeForm mensajeForm;

		mensajeForm = new MensajeForm();
		mensajeForm = this.mensajeService.reenviarMensaje(mensajeId);

		result = this.createEditModelAndView(mensajeForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MensajeForm mensajeForm, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;

		boolean cuerpoVacio = false;		
		String cuerpo = (String) request.getSession().getAttribute("cuerpoMensaje");
		request.removeAttribute("cuerpoMensaje");
		
		if(cuerpo == null || cuerpo.isEmpty() || cuerpo.equals("<p><br></p>") || cuerpo.equals("<br>")) {
			cuerpoVacio = true;
		}
		
		if (binding.hasErrors() || cuerpoVacio) {
			result = this.createEditModelAndView(mensajeForm);
			if(cuerpoVacio) {
				result.addObject("validaCuerpo", "mensaje.cuerpo.validacion");
			}
		} else {
			try {
				mensajeForm.setCuerpo(cuerpo);
				this.mensajeService.createMensaje(mensajeForm, false);
				result = new ModelAndView("redirect:/carpeta/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(mensajeForm, "mensaje.commit.error");
			}
		}

		return result;
	}
	
	

	@RequestMapping(value = "/mensajeAjax", method = RequestMethod.POST)
	public ResponseEntity<Object> mensajeAjax(@RequestBody final String cuerpo, final HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		String body = null;
		String error = null;
		
		// se usa para obtener solo el cuerpo del mensaje
		body = cuerpo.substring(11, cuerpo.length()-2);
        
        if(body == null
            	|| (!body.startsWith("<p>") && !body.startsWith("<p") && !body.startsWith("<ul>") && !body.startsWith("<ol>"))
            	|| (!body.endsWith("</p>") && !body.endsWith("</ul>") && !body.endsWith("</ol>"))) {
            	error = "1";
            }else {
//            	body = body.replaceAll("/\\<\\/p>(?=.*\\<\\/p>/g)", "");
            	String aux = body;
            	aux = aux.replaceAll("</p><p><br></p>", "<br><br>");
            	aux = aux.replaceAll("</p><p>", "<br>");
            	aux = aux.replaceAll("<p>", "");
            	aux = aux.replaceAll("</p>", "");
            	//Para escapar las dobles comillas
            	aux = aux.replaceAll("\\\\\"", "\"");
            	//Para escapar la barra inversa '\'
            	aux = aux.replaceAll("\\\\+", "\\\\");
//            	aux = "<p>" + aux + "</p>";
            	body = aux;
            	
            	request.getSession().setAttribute("cuerpoMensaje", body);
            }	
		
		return new ResponseEntity<Object>(error, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mensajeFeedback", method = RequestMethod.GET)
	public ResponseEntity<Object> mensajeFeedback(@RequestParam(value = "ofertaId", required = true) final int ofertaId, final HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		String body = null;
		Oferta oferta;
		MensajeForm mensajeForm;
		String dominio = "";
		String url = "";
		
		dominio = utilService.getDominio(request);
		url = "http://" + dominio + "/Gestion-Practicas/oferta/display.do?ofertaId=" + ofertaId;
		
		oferta = ofertaService.findOne(ofertaId);
		
		mensajeForm = new MensajeForm();
		mensajeForm.setAsunto("PETICIÓN DE FEEDBACK");
//		mensajeForm.setCuerpo("Se requiere feedback para la siguiente práctica: http://" + dominio + "/Gestion-Practicas/oferta/display.do?ofertaId=" + ofertaId + "<br /><br />- Este mensaje ha sido generado automáticamente -");
		mensajeForm.setCuerpo("Se requiere feedback para la siguiente práctica: <a href='" + url + "' target='_blank'>" + url + "</a><br /><br />- Este mensaje ha sido generado automáticamente -");
		mensajeForm.setIdReceptor(oferta.getTutorAsignado().getId());
		
		this.mensajeService.createMensaje(mensajeForm, true);	
			
		return new ResponseEntity<Object>(body, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contadorMsg", method = RequestMethod.POST)
	public ResponseEntity<Object> contadorMsg() {
		HttpHeaders headers = new HttpHeaders();
		String body = null;
		Actor actor;		
		
		actor = actorService.findByPrincipal();
        
		body = mensajeService.numMsgNoLeidos(actor.getId()).toString();        
		
		return new ResponseEntity<Object>(body, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = true) final int mensajeId) {
		ModelAndView result;
		MensajeForm mensajeForm;
		Mensaje mensaje;

		mensaje = this.mensajeService.findOne(mensajeId);

		try {
			this.mensajeService.delete(mensaje);
			result = new ModelAndView("redirect:/carpeta/list.do");
		} catch (final Throwable oops) {
			mensajeForm = this.mensajeService.createMensajeForm(mensaje.getId());
			result = this.createEditModelAndView(mensajeForm, "mensaje.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final MensajeForm mensajeForm) {
		ModelAndView result;

		result = this.createEditModelAndView(mensajeForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MensajeForm mensajeForm, final String message) {
		ModelAndView result;
		Collection<Actor> actores;
		actores = this.actorService.findAllActivos();

		result = new ModelAndView("mensaje/create");
		result.addObject("mensajeForm", mensajeForm);
		result.addObject("message", message);
		result.addObject("actores", actores);

		return result;
	}

}
