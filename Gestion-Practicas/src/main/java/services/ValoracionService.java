
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Oferta;
import domain.Valoracion;
import forms.ValoracionForm;
import repositories.ValoracionRepository;

@Service
@Transactional
public class ValoracionService {

	// Managed repository -----------------------------------------------------
	
	@Autowired
	private ValoracionRepository valoracionRepository;

	// Supporting Services ----------------------------------------------------
	
	@Autowired
	private OfertaService ofertaService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------
	public ValoracionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

//	public Actor create() {
//		Authority authority;
//		UserAccount userAccount;
//		Actor result;
//
//		authority = new Authority();
//		authority.setAuthority("ALUMNO");
//
//		userAccount = this.userAccountService.create();
//
//		result = new Actor();
//
//		userAccount.addAuthority(authority);
//		result.setUserAccount(userAccount);
//
//		return result;
//
//	}

	public Valoracion findOne(final int valoracionId) {
		Assert.isTrue(valoracionId != 0);

		Valoracion result;

		result = this.valoracionRepository.findOne(valoracionId);

		return result;
	}

	public Collection<Valoracion> findAll() {
		Collection<Valoracion> result;

		result = this.valoracionRepository.findAll();

		return result;
	}

	public Valoracion save(final Valoracion valoracion) {
		Valoracion result;

		Assert.notNull(valoracion);

		result = this.valoracionRepository.save(valoracion);

		return result;
	}

	public void delete(final Valoracion valoracion) {
		this.valoracionRepository.delete(valoracion);
	}

	// Other business methods -------------------------------------------------	

	public ValoracionForm takeForm(final Valoracion valoracion) {
		ValoracionForm valoracionForm;

		valoracionForm = new ValoracionForm();

		valoracionForm.setId(valoracion.getId());
		valoracionForm.setTexto(valoracion.getTexto());
		valoracionForm.setNotaCurricular(valoracion.getNotaCurricular());
		valoracionForm.setNotaExtracurricular(valoracion.getNotaExtracurricular());		

		return valoracionForm;
	}

	public Valoracion reconstruct(final ValoracionForm valoracionForm) {
		Valoracion res;
		Oferta oferta;
		
		if(valoracionForm.getId() != 0) {
			res = this.findOne(valoracionForm.getId());
		}else {
			res = new Valoracion();
		}
		
		res.setTexto(valoracionForm.getTexto());
		res.setNotaCurricular(valoracionForm.getNotaCurricular());
		res.setNotaExtracurricular(valoracionForm.getNotaExtracurricular());		
		
		oferta = ofertaService.findOne(valoracionForm.getIdOferta());
		res.setOferta(oferta);
		

		return res;
	}	
	

}
