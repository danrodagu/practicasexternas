
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Oferta;
import forms.RegistroOfertaForm;
import repositories.OfertaRepository;

@Service
@Transactional
public class OfertaService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private OfertaRepository ofertaRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private AlumnoService alumnoService;
	
	// Constructors -----------------------------------------------------------

	public OfertaService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Oferta create(final Oferta nuevaOferta) {
		Oferta res;

		res = new Oferta();

		res.setDescripcion(nuevaOferta.getDescripcion());
		res.setDotacion(nuevaOferta.getDotacion());
		res.setDuracion(nuevaOferta.getDuracion());
		res.setEmpresa(nuevaOferta.getEmpresa());
		res.setEsCurricular(nuevaOferta.getEsCurricular());
		res.setLocalidad(nuevaOferta.getLocalidad());
		res.setProvincia(nuevaOferta.getProvincia());
		res.setPais(nuevaOferta.getPais());
		res.setTitulo(nuevaOferta.getTitulo());

		return res;

	}

	public Oferta findOne(final int ofertaId) {
		Oferta result;

		result = this.ofertaRepository.findOne(ofertaId);

		return result;
	}

	public Collection<Oferta> findAll() {
		Collection<Oferta> result;

		result = this.ofertaRepository.findAll();

		return result;
	}

	public Oferta save(final Oferta oferta) {
		Oferta result;

		Assert.notNull(oferta);

		result = this.ofertaRepository.save(oferta);

		return result;

	}

//	public void delete(final Oferta oferta) {
//
//		Assert.notNull(oferta);
//
//		this.ofertaRepository.delete(oferta);
//
//	}

	// Other business methods -------------------------------------------------


	public Collection<Oferta> ofertasByEmpresa(final String nombreEmpresa) {
		final Collection<Oferta> result;

//		Assert.isTrue(this.actorService.isCoordinador());

		result = this.ofertaRepository.ofertasByEmpresa(nombreEmpresa);

		return result;

	}


	public Collection<Oferta> ofertasByPais(final String pais) {
		final Collection<Oferta> result;

		result = this.ofertaRepository.ofertasByPais(pais);

		return result;

	}
	
	public void registrarOferta(final RegistroOfertaForm registroOfertaForm) {		
		Actor alumno;
		Oferta oferta;
		Actor tutor;

		tutor = tutorService.findOne(registroOfertaForm.getIdTutor());		
		
		alumno = alumnoService.findOne(registroOfertaForm.getIdAlumno());
		
		oferta = new Oferta();
		oferta.setTitulo(registroOfertaForm.getTitulo());
		oferta.setDescripcion(registroOfertaForm.getDescripcion());
		oferta.setDotacion(registroOfertaForm.getDotacion());
		oferta.setDuracion(registroOfertaForm.getDuracion());
		oferta.setHoras(registroOfertaForm.getHoras());
		oferta.setFechaInicio(registroOfertaForm.getFechaInicio());
		oferta.setFechaFin(registroOfertaForm.getFechaFin());
		oferta.setEmpresa(registroOfertaForm.getEmpresa());
		oferta.setCifEmp(registroOfertaForm.getCifEmp());
		oferta.setEmailEmp(registroOfertaForm.getEmailEmp());
		oferta.setTelefonoEmp(registroOfertaForm.getTelefonoEmp());
		oferta.setTutorEmp(registroOfertaForm.getTutorEmp());
		oferta.setEsCurricular(registroOfertaForm.getEsCurricular());
		oferta.setLocalidad(registroOfertaForm.getLocalidad());
		oferta.setProvincia(registroOfertaForm.getProvincia());
		oferta.setPais(registroOfertaForm.getPais());
		oferta.setExpedienteCerrado(false);
		oferta.setAlumnoAsignado(alumno);
		oferta.setTutorAsignado(tutor);
		
		oferta = save(oferta);
		
	}
	

}
