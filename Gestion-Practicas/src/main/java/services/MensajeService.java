
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Carpeta;
import domain.Mensaje;
import forms.MensajeForm;
import repositories.MensajeRepository;

@Service
@Transactional
public class MensajeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MensajeRepository	mensajeRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CarpetaService	carpetaService;


	// Constructors -----------------------------------------------------------
	public MensajeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Mensaje create() {
		Mensaje result;

		result = new Mensaje();

		return result;
	}

	public Collection<Mensaje> findAll() {
		Collection<Mensaje> result;

		result = this.mensajeRepository.findAll();

		return result;
	}

	public Mensaje findOne(final int mensajeId) {
		Mensaje result;

		result = this.mensajeRepository.findOne(mensajeId);
		Assert.notNull(result);

		return result;
	}

	public Mensaje save(Mensaje mensaje) {
		Date Fecha;
		Mensaje m;

		// Copia mensaje para receptor
		if (mensaje.getId() == 0) {

			Fecha = new Date();
			Fecha.setTime(Fecha.getTime() - 1);
			mensaje.setFecha(Fecha);

			Carpeta inbox = null;
			inbox = this.carpetaService.findCarpetaByNombreAndActor("Inbox", mensaje.getReceptor().getId());
			Assert.notNull(inbox);

			m = new Mensaje();

			m.setCarpeta(inbox);
			m.setFecha(Fecha);
			m.setCuerpo(mensaje.getCuerpo());
			m.setAsunto(mensaje.getAsunto());
			m.setReceptor(mensaje.getReceptor());
			m.setEmisor(mensaje.getEmisor());
//			m.setAttachments(mensaje.getAttachments());
			m = this.mensajeRepository.save(m);
		}

		mensaje = this.mensajeRepository.save(mensaje);

		return mensaje;

	}

	public void delete(final Mensaje mensaje) {

		Carpeta container;
		Carpeta trashbox = null;
		Actor a;

		a = this.actorService.findByPrincipal();

		Assert.notNull(mensaje);
		Assert.isTrue(mensaje.getCarpeta().getActor().getId() == a.getId());
		container = mensaje.getCarpeta();

		if (container.getNombre().equals("Trashbox")) {
			this.mensajeRepository.delete(mensaje);
		} else {
			trashbox = this.carpetaService.findCarpetaByNombreAndActor("Trashbox", a.getId());
			Assert.notNull(trashbox);

			mensaje.setCarpeta(trashbox);
			this.save(mensaje);
		}

	}

	// other methods ----------------------------------------------------------

	public Mensaje createMensaje(final MensajeForm mensajeForm) {
		Mensaje mensaje;
		Actor emisor;
		Actor receptor;
		Carpeta outbox = null;

		// Recupero el actor que está enviando el mensaje
		emisor = this.actorService.findByPrincipal();

		// Recupero el actor que recibe el mensaje		
		receptor = actorService.findOne(mensajeForm.getIdReceptor());

		// Guardo el outbox del actor que envía el mensaje
		outbox = this.carpetaService.findCarpetaByNombreAndActor("Outbox", this.actorService.findByPrincipal().getId());

		mensaje = this.create();

		mensaje.setCuerpo(mensajeForm.getCuerpo());
		mensaje.setAsunto(mensajeForm.getAsunto());
		mensaje.setEmisor(emisor);
		mensaje.setReceptor(receptor);
//		mensaje.setAttachments(mensajeForm.getAttachments());
		mensaje.setCarpeta(outbox);

		mensaje = this.save(mensaje);
		return mensaje;
	}

	public MensajeForm createMensajeForm(final int mensajeId) {
		MensajeForm result;
		Mensaje mensaje;

		mensaje = this.findOne(mensajeId);
		result = new MensajeForm();

		result.setCuerpo(mensaje.getCuerpo());
		result.setAsunto(mensaje.getAsunto());
		result.setIdReceptor(mensaje.getReceptor().getId());
//		result.setAttachments(mensaje.getAttachments());

		return result;
	}

	public MensajeForm responderMensaje(final int mensajeId) {
		MensajeForm result;
		Mensaje mensaje;
		Actor principal;
		Actor receptor;

		result = new MensajeForm();
		mensaje = this.findOne(mensajeId);
		principal = this.actorService.findByPrincipal();

		Assert.isTrue(principal.getId() == mensaje.getReceptor().getId() || principal.getId() == mensaje.getEmisor().getId());

		if (mensaje.getReceptor().getId() != principal.getId()) {
			receptor = mensaje.getReceptor();
		} else {
			receptor = mensaje.getEmisor();
		}
		result.setIdReceptor(receptor.getId());

		return result;

	}
	
	public MensajeForm reenviarMensaje(final int mensajeId) {
		MensajeForm result;
		Mensaje mensaje;
		String cuerpo;
		Actor actor;

		result = new MensajeForm();
		mensaje = this.findOne(mensajeId);
		Assert.notNull(mensaje);
		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor.getId() == mensaje.getEmisor().getId() || actor.getId() == mensaje.getReceptor().getId());

		cuerpo = " \r ----Mensaje enviado----- \r De: " + mensaje.getEmisor().getUserAccount().getUsername() + "\r Fecha: " + mensaje.getFecha() + "\r Cuerpo: " + mensaje.getCuerpo() + "\r Asunto:" + mensaje.getAsunto();

		result.setCuerpo(cuerpo);

		return result;

	}
	

	public Collection<Mensaje> findMensajesByCarpeta(final int carpetaId) {
		Collection<Mensaje> result;
		Carpeta carpeta;

		carpeta = this.carpetaService.findOne(carpetaId);

		Assert.isTrue(carpeta.getActor().getId() == this.actorService.findByPrincipal().getId());

		result = this.mensajeRepository.findMensajesByCarpeta(carpetaId);

		return result;

	}	

}
