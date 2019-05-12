
package services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
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

	public Mensaje save(Mensaje mensaje, final boolean esAutomatico) {
		Date fecha;
		Mensaje m;

		
		if (mensaje.getId() == 0) {
			fecha = new Date();
			fecha.setTime(fecha.getTime() - 1);
			mensaje.setFecha(fecha);			
			
			if(!esAutomatico) {
				// Copia mensaje para el actor que envía
				Carpeta outbox = null;
				outbox = this.carpetaService.findCarpetaByNombreAndActor("Enviado", this.actorService.findByPrincipal().getId());
				Assert.notNull(outbox);

				m = new Mensaje();

				m.setCarpeta(outbox);
				m.setFecha(fecha);
				m.setCuerpo(mensaje.getCuerpo());
				m.setAsunto(mensaje.getAsunto());
				m.setLeido(true);
				m.setReceptor(mensaje.getReceptor());
				m.setEmisor(mensaje.getEmisor());
				//Guardamos mensaje en enviados del emisor
				m = this.mensajeRepository.save(m);		
			}			
				
		}
		
		// Guardamos mensaje en recibidos del receptor
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

		if (container.getNombre().equals("Papelera")) {
			this.mensajeRepository.delete(mensaje);
		} else {
			trashbox = this.carpetaService.findCarpetaByNombreAndActor("Papelera", a.getId());
			Assert.notNull(trashbox);

			mensaje.setCarpeta(trashbox);
			this.save(mensaje, false);
		}

	}

	// other methods ----------------------------------------------------------

	public Mensaje createMensaje(final MensajeForm mensajeForm, final boolean esAutomatico) {
		Mensaje mensaje;
		Actor emisor;
		Actor receptor;
		Carpeta inbox = null;

		// Recupero el actor que está enviando el mensaje
		emisor = this.actorService.findByPrincipal();

		// Recupero el actor que recibe el mensaje		
		receptor = actorService.findOne(mensajeForm.getIdReceptor());

		// Guardo el inbox del actor que recibe el mensaje
		inbox = this.carpetaService.findCarpetaByNombreAndActor("Recibido", receptor.getId());		

		mensaje = this.create();

		mensaje.setCuerpo(mensajeForm.getCuerpo());
		mensaje.setAsunto(mensajeForm.getAsunto());
		mensaje.setLeido(false);
		mensaje.setEmisor(emisor);
		mensaje.setReceptor(receptor);
//		mensaje.setAttachments(mensajeForm.getAttachments());
		mensaje.setCarpeta(inbox);

		mensaje = this.save(mensaje, esAutomatico);
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
		String cuerpo;
		LocalDate localDate;
		String day;
		String month;
		String year;
		String fechaMensajeAnterior;
		Calendar calendar;
		String horaMensajeAnterior;
		Integer minuto;
		String fechaMensajeActual;
		String horaMensajeActual;

		result = new MensajeForm();
		mensaje = this.findOne(mensajeId);
		mensaje.setLeido(true);
		principal = this.actorService.findByPrincipal();

		Assert.isTrue(principal.getId() == mensaje.getReceptor().getId() || principal.getId() == mensaje.getEmisor().getId());

		if (mensaje.getReceptor().getId() != principal.getId()) {
			receptor = mensaje.getReceptor();
		} else {
			receptor = mensaje.getEmisor();
		}
		result.setIdReceptor(receptor.getId());
		
		if(!mensaje.getAsunto().startsWith("RE:") && !mensaje.getAsunto().startsWith("FW:")) {
			result.setAsunto("RE: " + mensaje.getAsunto());
		}else {
			result.setAsunto(mensaje.getAsunto());
		}
		
		//fecha y hora mensaje anterior
		localDate = mensaje.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();		
		day = String.valueOf(localDate.getDayOfMonth());
		month = String.valueOf(localDate.getMonthValue());
		year = String.valueOf(localDate.getYear());
		
		if(day.length() == 1) {
			day = "0" + day;
		}
		if(month.length() == 1) {
			month = "0" + month;
		}		
		
		fechaMensajeAnterior = day + "/" + month + "/" + year;
		
		calendar = Calendar.getInstance();
		calendar.setTime(mensaje.getFecha());
		minuto = calendar.get(Calendar.MINUTE);
		
		horaMensajeAnterior = calendar.get(Calendar.HOUR_OF_DAY) + ":" + ((minuto.toString().length() == 1) ? "0" + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE));
		
		//fecha y hora mensaje actual
		localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();		
		day = String.valueOf(localDate.getDayOfMonth());
		month = String.valueOf(localDate.getMonthValue());
		year = String.valueOf(localDate.getYear());
		
		if(day.length() == 1) {
			day = "0" + day;
		}
		if(month.length() == 1) {
			month = "0" + month;
		}	
		
		fechaMensajeActual = day + "/" + month + "/" + year;
		
		calendar = Calendar.getInstance();
		minuto = calendar.get(Calendar.MINUTE);
		
		horaMensajeActual = calendar.get(Calendar.HOUR_OF_DAY) + ":" + ((minuto.toString().length() == 1) ? "0" + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE));
		
		
		if(mensaje.getCuerpo().contains("__________________________________")) {
			String[] cuerpoAux = mensaje.getCuerpo().split("__________________________________");
			if(cuerpoAux.length > 1) {
				cuerpo = " <span style='color: rgb(0, 71, 178);'>El " + fechaMensajeAnterior + " a las " + horaMensajeAnterior + ", " + mensaje.getEmisor().getUserAccount().getUsername() + " escribió: <br /><br /></span><span style='color: rgb(0, 71, 178);'>" + cuerpoAux[1] + "</span><br />__________________________________<br /><br />Escribe tu respuesta aquí";
			}else {
				cuerpo = " <span style='color: rgb(0, 71, 178);'>El " + fechaMensajeAnterior + " a las " + horaMensajeAnterior + ", " + mensaje.getEmisor().getUserAccount().getUsername() + " escribió: <br /><br /></span><span style='color: rgb(0, 71, 178);'>" + cuerpoAux[0] + "</span><br />__________________________________<br /><br />Escribe tu respuesta aquí";
			}
		}else {
			cuerpo = " <span style='color: rgb(0, 71, 178);'>El " + fechaMensajeAnterior + " a las " + horaMensajeAnterior + ", " + mensaje.getEmisor().getUserAccount().getUsername() + " escribió: <br /><br /></span><span style='color: rgb(0, 71, 178);'>" + mensaje.getCuerpo() + "</span><br />__________________________________<br /><br />Escribe tu respuesta aquí";
		}
		
		
		result.setCuerpo(cuerpo);

		return result;

	}
	
	public MensajeForm reenviarMensaje(final int mensajeId) {
		MensajeForm result;
		Mensaje mensaje;
		String cuerpo;
		Actor actor;
		LocalDate localDate;
		String day;
		String month;
		String year;
		String fecha;
		Calendar calendar;
		String hora;
		Integer minuto;

		result = new MensajeForm();
		mensaje = this.findOne(mensajeId);
		mensaje.setLeido(true);
		Assert.notNull(mensaje);
		actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor.getId() == mensaje.getEmisor().getId() || actor.getId() == mensaje.getReceptor().getId());

		localDate = mensaje.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();		
		day = String.valueOf(localDate.getDayOfMonth());
		month = String.valueOf(localDate.getMonthValue());
		year = String.valueOf(localDate.getYear());
		
		if(day.length() == 1) {
			day = "0" + day;
		}
		if(month.length() == 1) {
			month = "0" + month;
		}
		
		fecha = day + "/" + month + "/" + year;
		
		calendar = Calendar.getInstance();
		calendar.setTime(mensaje.getFecha());
		minuto = calendar.get(Calendar.MINUTE);		
		
		hora = calendar.get(Calendar.HOUR_OF_DAY) + ":" + ((minuto.toString().length() == 1) ? "0" + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE));
		
		if(mensaje.getCuerpo().contains("__________________________________")) {
			String[] cuerpoAux = mensaje.getCuerpo().split("__________________________________");
			if(cuerpoAux.length > 1) {
				cuerpo = " <span style='color: rgb(0, 71, 178);'>----Mensaje enviado----<br /><br />  El " + fecha + " a las " + hora + ", " + mensaje.getEmisor().getUserAccount().getUsername() + " escribió: <br /><br /></span><span style='color: rgb(0, 71, 178);'>" + cuerpoAux[1] + "</span><br />__________________________________<br /><br />Escribe tu texto aquí";
			}else {
				cuerpo = " <span style='color: rgb(0, 71, 178);'>----Mensaje enviado----<br /><br />  El " + fecha + " a las " + hora + ", " + mensaje.getEmisor().getUserAccount().getUsername() + " escribió: <br /><br /></span><span style='color: rgb(0, 71, 178);'>" + cuerpoAux[0] + "</span><br />__________________________________<br /><br />Escribe tu texto aquí";
			}
		}else {
			cuerpo = " <span style='color: rgb(0, 71, 178);'>----Mensaje enviado----<br /><br />  El " + fecha + " a las " + hora + ", " + mensaje.getEmisor().getUserAccount().getUsername() + " escribió: <br /><br /></span><span style='color: rgb(0, 71, 178);'>" + mensaje.getCuerpo() + "</span><br />__________________________________<br /><br />Escribe tu texto aquí";
		}
		
//		cuerpo = " ----Mensaje enviado----<br /><br />  El " + fecha + " a las " + hora + ", " + mensaje.getEmisor().getUserAccount().getUsername() + " escribió: <br /><br />" + mensaje.getCuerpo();
		
		if(!mensaje.getAsunto().startsWith("FW:") && !mensaje.getAsunto().startsWith("RE:")) {
			result.setAsunto("FW: " + mensaje.getAsunto());
		}else {
			result.setAsunto(mensaje.getAsunto());
		}
		result.setCuerpo(cuerpo);

		return result;

	}
	

	public Collection<Mensaje> findMensajesByCarpeta(final int carpetaId) {
		Collection<Mensaje> result;
		Carpeta carpeta;
		Actor principal = this.actorService.findByPrincipal();

		carpeta = this.carpetaService.findOne(carpetaId);

		Assert.isTrue(carpeta.getActor().getId() == principal.getId());

		result = this.mensajeRepository.findMensajesByCarpeta(carpetaId);

		return result;

	}	
	
	public Integer numMsgNoLeidos(final int actorId) {
		Integer result;
		Carpeta carpeta;

		carpeta = this.carpetaService.findCarpetaByNombreAndActor("Recibido", actorId);

		result = this.mensajeRepository.numMsgNoLeidos(carpeta.getId());

		return result;

	}	

}
