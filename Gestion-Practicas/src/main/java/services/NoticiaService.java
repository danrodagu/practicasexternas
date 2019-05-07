
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Noticia;
import forms.NoticiaForm;
import repositories.NoticiaRepository;

@Service
@Transactional
public class NoticiaService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private NoticiaRepository	noticiaRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------
	public NoticiaService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Noticia create() {
		Noticia result;

		result = new Noticia();

		return result;
	}

	public Collection<Noticia> findAll() {
		Collection<Noticia> result;

		result = this.noticiaRepository.findAllOrderedByDate();

		return result;
	}

	public Noticia findOne(final int noticiaId) {
		Noticia result;

		result = this.noticiaRepository.findOne(noticiaId);
		Assert.notNull(result);

		return result;
	}

	public Noticia save(Noticia noticia) {		

		noticia = this.noticiaRepository.save(noticia);

		return noticia;

	}

	public void delete(final Noticia noticia) {
		
		this.noticiaRepository.delete(noticia);
	}

	// other methods ----------------------------------------------------------

	public NoticiaForm takeForm(final Noticia noticia) {
		NoticiaForm result;
		
		result = new NoticiaForm();

		result.setId(noticia.getId());
		result.setCuerpo(noticia.getCuerpo());
		result.setTitulo(noticia.getTitulo());

		return result;
	}
	
	public Noticia reconstruct(final NoticiaForm noticiaForm) {
		Noticia noticia;
		Actor autor;
		Date fechaModificacion;

		// Recupero el actor que está creando la noticia
		autor = this.actorService.findByPrincipal();

		if(noticiaForm.getId() == 0) {
			noticia = this.create();
		}else {
			noticia = findOne(noticiaForm.getId());
		}
		
		fechaModificacion = new Date();
		fechaModificacion.setTime(fechaModificacion.getTime() - 1);

		noticia.setCuerpo(noticiaForm.getCuerpo());
		noticia.setTitulo(noticiaForm.getTitulo());
		noticia.setFechaModificacion(fechaModificacion);
		noticia.setAutor(autor);
		
		return noticia;
	}

	

}
