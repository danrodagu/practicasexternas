
package services;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Token;
import repositories.ActorRepository;
import repositories.CarpetaRepository;
import repositories.DocumentoRepository;
import repositories.MensajeRepository;
import repositories.NoticiaRepository;
import repositories.OfertaRepository;
import repositories.TokenRepository;
import repositories.ValoracionRepository;

@Service
@Transactional
public class UtilService {
	
	@Autowired
	private ActorRepository actorRepository;
	
	@Autowired
	private CarpetaRepository carpetaRepository;
	
	@Autowired
	private DocumentoRepository documentoRepository;
	
	@Autowired
	private MensajeRepository	mensajeRepository;
	
	@Autowired
	private NoticiaRepository	noticiaRepository;
	
	@Autowired
	private OfertaRepository ofertaRepository;
	
	@Autowired
	private ValoracionRepository valoracionRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	public UtilService() {
		super();
	}
	
	
	public int maximoIdDB() {
//		Query idActor = Actor.entityManager().createQuery("SELECT MAX(a.id) FROM Actor a");
//		Query idCarpeta = Actor.entityManager().createQuery("SELECT MAX(a.id) FROM Carpeta a");
//		Query idDocumento = Actor.entityManager().createQuery("SELECT MAX(a.id) FROM Documento a");
//		Query idMensaje = Actor.entityManager().createQuery("SELECT MAX(a.id) FROM Mensaje a");
//		Query idOferta = Actor.entityManager().createQuery("SELECT MAX(a.id) FROM Oferta a");
//		Query idValoracion = Actor.entityManager().createQuery("SELECT MAX(a.id) FROM Valoracion a");
		
		
		Integer idActor = actorRepository.maxActorId();
		Integer idCarpeta = carpetaRepository.maxCarpetaId();
		Integer idDocumento = documentoRepository.maxDocumentoId();
		Integer idMensaje = mensajeRepository.maxMensajeId();
		Integer idNoticia = noticiaRepository.maxNoticiaId();
		Integer idOferta = ofertaRepository.maxOfertaId();
		Integer idValoracion = valoracionRepository.maxValoracionId();
		Integer idToken = tokenRepository.maxTokenId();
		
		Integer[] ids = {idActor,idCarpeta,idDocumento,idMensaje,idNoticia,idOferta,idValoracion,idToken};
		
		Integer maxId = 0;
		
		for(Integer id : ids) {			
			if(id != null && id > maxId) {
				maxId = id;
			}
		}	
		
		return maxId;		
	}
	
	public boolean tokenExpirado(final Token token) {
		Date fechaActual = new Date();
		Date fechaExpiracion;
		Calendar fechaAux;
		
		if(token == null) {
			return true;
		}
		
		fechaAux = Calendar.getInstance();
		fechaAux.setTime(token.getFechaCreacion());
		fechaAux.add(Calendar.DAY_OF_YEAR, 1);
		
		fechaExpiracion = fechaAux.getTime();
		
		if(fechaActual.after(fechaExpiracion)) {
			return true;
		}else {
			return false;
		}		
	}
	
	public String getDominio(final HttpServletRequest request) {
		String res = null;
		StringBuffer requestURL = request.getRequestURL();
		if (request.getQueryString() != null) {
		    requestURL.append("?").append(request.getQueryString());
		}
		String completeURL = requestURL.toString(); //http://localhost:8080/Gestion-Practicas/...
		Pattern pattern = Pattern.compile("http:\\/\\/(.*?)\\/"); //Se coge el dominio entre 'http://' y '/'
		Matcher matcher = pattern.matcher(completeURL);
		
		if (matcher.find()){
			res = matcher.group(1);
		    System.out.println(res);
		}else {
			System.out.println("No se ha encontrado dominio");
		}
		
		return res;
	}
	
}
