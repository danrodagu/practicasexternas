
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Documento;
import forms.DocumentoForm;
import repositories.DocumentoRepository;
import security.UserAccountService;

@Service
@Transactional
public class DocumentoService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private DocumentoRepository documentoRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------
	public DocumentoService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Documento findOne(final int documentoId) {
		Assert.isTrue(documentoId != 0);

		Documento result;

		result = this.documentoRepository.findOne(documentoId);

		return result;
	}

	public Collection<Documento> findAll() {
		Collection<Documento> result;

		result = this.documentoRepository.findAll();

		return result;
	}

	public Documento save(final Documento documento) {
		Documento result;

		result = this.documentoRepository.save(documento);

		return result;
	}

	public void delete(final Documento documento) {
		this.documentoRepository.delete(documento);
	}

	// Other business methods -------------------------------------------------

	public Collection<Documento> findDocumentosByFormato(final String formato) {
		Collection<Documento> res;

		res = this.documentoRepository.findDocumentosByFormato(formato.toUpperCase());

		return res;
	}
	
	public Collection<Documento> findDocumentosByAlumno(final int actorId) {
		Collection<Documento> res;

		res = this.documentoRepository.findDocumentosByAlumno(actorId);

		return res;
	}	
	
	
//	public Collection<Documento> findDocumentosByTutorPrincipal(final int alumnoId) {
//		Collection<Documento> res;		
//		
//
//		res = this.documentoRepository.findDocumentosByAlumnoPrincipal(alumnoId);
//
//		return res;
//	}
	
//	public DocumentoForm takeForm(final Documento documento) {
//		DocumentoForm documentoForm;
//
//		documentoForm = new DocumentoForm();
//
//		documentoForm.setId(documento.getId());
//		documentoForm.setArchivo(documento.getArchivo());
//
//		return documentoForm;
//	}

	public Documento reconstruct(final DocumentoForm documentoForm) {
		Documento res;
		Actor uploader;

		if (documentoForm.getId() == 0) {
			res = new Documento();
			uploader = actorService.findByPrincipal();
			res.setUploader(uploader);
			
			if(actorService.isAlumno()) {
				res.setAlumno(uploader);
			}
		} else {
			res = this.findOne(documentoForm.getId());
		}

		res.setArchivo(documentoForm.getArchivo());		

		return res;
	}

}
