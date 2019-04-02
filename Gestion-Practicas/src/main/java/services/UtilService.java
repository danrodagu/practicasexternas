
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import repositories.CarpetaRepository;
import repositories.DocumentoRepository;
import repositories.MensajeRepository;
import repositories.NoticiaRepository;
import repositories.OfertaRepository;
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
		
		Integer[] ids = {idActor,idCarpeta,idDocumento,idMensaje,idNoticia,idOferta,idValoracion};
		
		Integer maxId = 0;
		
		for(Integer id : ids) {			
			if(id != null && id > maxId) {
				maxId = id;
			}
		}	
		
		return maxId;		
	}
	
}
