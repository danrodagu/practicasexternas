package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Carpeta;
import forms.CarpetaForm;
import repositories.CarpetaRepository;

@Service
@Transactional
public class CarpetaService {

	// Managed repository -------------------------------
	@Autowired
	private CarpetaRepository carpetaRepository;

	// Supporting services ------------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private MensajeService mensajeService;

	// Constructor --------------------------------------
	public CarpetaService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public Carpeta create() {
		Carpeta result;

		result = new Carpeta();

		return result;
	}

	public Collection<Carpeta> findAll() {
		Collection<Carpeta> result;

		result = this.carpetaRepository.findAll();

		return result;
	}

	public Carpeta findOne(final int carpetaId) {
		Carpeta result;

		result = this.carpetaRepository.findOne(carpetaId);

		return result;
	}

	public Carpeta save(final Carpeta carpeta) {
		Assert.notNull(carpeta);
		Carpeta result;

		result = this.carpetaRepository.save(carpeta);

		return result;
	}

	public void delete(final Carpeta carpeta) {
		Assert.notNull(carpeta);
		Assert.isTrue(this.mensajeService.findMensajesByCarpeta(carpeta.getId())
				.size() == 0);
		Assert.isTrue(carpeta.getActor().getId() == this.actorService
				.findByPrincipal().getId());
		Assert.isTrue(carpeta.isNoModificable() == false);

		this.carpetaRepository.delete(carpeta);
	}

	// Other methods -------------------------------------

	// Este metodo crea los tres carpetas de un actor, se le debe pasar el actor
	// ya instrumentado
	public void carpetasPorDefecto(final Actor actor) {
		Carpeta inbox;
		Carpeta outbox;
		Carpeta trashbox;

		Assert.notNull(actor);

		inbox = this.create();
		outbox = this.create();
		trashbox = this.create();

		// Se les asigna el actor
		inbox.setActor(actor);
		inbox.setNombre("Recibido");
		inbox.setNoModificable(true);

		outbox.setActor(actor);
		outbox.setNombre("Enviado");
		outbox.setNoModificable(true);

		trashbox.setActor(actor);
		trashbox.setNombre("Papelera");
		trashbox.setNoModificable(true);

		// Por último se hacen persistentes los objetos en la bd
		this.save(inbox);
		this.save(outbox);
		this.save(trashbox);
	}

	public Collection<Carpeta> findCarpetaByActor(final int actorId) {
		Collection<Carpeta> carpetas;

		carpetas = this.carpetaRepository.findCarpetasByActor(actorId);

		return carpetas;
	}

	public Carpeta findCarpetaByNombreAndActor(final String nombre, final int actorId) {
		Carpeta carpeta;

		carpeta = this.carpetaRepository.findCarpetaByNombreAndActor(nombre, actorId);

		return carpeta;
	}

	
	public Carpeta reconstruct(final CarpetaForm carpetaForm) {
		Carpeta carpeta;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		if (carpetaForm.getId() == 0) {
			carpeta = this.create();
			carpeta.setActor(actor);
			carpeta.setNoModificable(false);
		} else {
			carpeta = this.findOne(carpetaForm.getId());
			Assert.isTrue(carpeta.getActor().getId() == actor.getId());
		}

		carpeta.setNombre(carpetaForm.getNombre());

		carpeta = this.save(carpeta);
		return carpeta;
	}

	// Crea un carpetaForm a partir de un Carpeta
	public CarpetaForm copiarCarpeta(final int carpetaId) {
		CarpetaForm result;
		Carpeta carpeta;

		result = new CarpetaForm();
		carpeta = this.findOne(carpetaId);

		result.setId(carpeta.getId());
		result.setNombre(carpeta.getNombre());

		return result;
	}

	// Elimina un carpeta a partir de un CarpetaForm
	public void deleteCarpeta(final CarpetaForm carpetaForm) {
		Carpeta carpeta;
		Actor actor;

		carpeta = this.findOne(carpetaForm.getId());
		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor.getId() == carpeta.getActor().getId());

		this.delete(carpeta);
	}

	public boolean noExisteCarpeta(final String nombre) {
		Actor actor;
		Carpeta carpeta;
		boolean result = false;

		actor = this.actorService.findByPrincipal();
		carpeta = this.findCarpetaByNombreAndActor(nombre, actor.getId());

		if (carpeta == null) {
			result = true;
		}

		return result;
	}

}
