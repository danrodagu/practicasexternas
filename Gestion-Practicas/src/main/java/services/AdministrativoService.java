
package services;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import forms.AdministrativoForm;
import forms.BusquedaAdministrativosForm;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class AdministrativoService {

	@PersistenceContext( unitName="Gestion-Practicas" )
	private EntityManager em;
	
	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository actorRepository;

	// Supporting Services ----------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------
	public AdministrativoService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Actor create() {
		Authority authority;
		UserAccount userAccount;
		Actor result;

		authority = new Authority();
		authority.setAuthority("ADMINISTRATIVO");

		userAccount = this.userAccountService.create();

		result = new Actor();

		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		result.getUserAccount().setEnabled(true);

		return result;

	}

	public Actor findOne(final int administrativoId) {
		Assert.isTrue(administrativoId != 0);

		Actor result;

		result = this.actorRepository.findOne(administrativoId);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAllAdministrativos();

		return result;
	}
	
	public Collection<Actor> findAllActivos() {
		Collection<Actor> result;

		result = this.actorRepository.findAllAdministrativosActivos();

		return result;
	}

	public Actor save(final Actor administrativo) {
		Actor result;
		BCryptPasswordEncoder encoder;

		encoder = new BCryptPasswordEncoder();

		administrativo.getUserAccount().setPassword(encoder.encode(administrativo.getUserAccount().getPassword()));

		result = this.actorRepository.save(administrativo);

		return result;
	}

	public void delete(final Actor administrativo) {
		this.actorRepository.delete(administrativo);
	}

	// Other business methods -------------------------------------------------

	public Actor findByUsername(final String username) {
		Actor res;

		res = this.actorRepository.findByUsername(username);

		return res;
	}

	public Actor findByPrincipal() {

		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = this.actorRepository.findByPrincipal(userAccount.getId());

		Assert.notNull(result);

		return result;

	}
	
	public AdministrativoForm takeForm(final Actor administrativo) {
		AdministrativoForm administrativoForm;

		administrativoForm = new AdministrativoForm();

		administrativoForm.setId(administrativo.getId());
		administrativoForm.setNombre(administrativo.getNombre());
		administrativoForm.setApellidos(administrativo.getApellidos());
		administrativoForm.setNif(administrativo.getNif());
		administrativoForm.setEmail(administrativo.getEmail());

		// administrativoForm.setPicture(administrativo.getPicture());
		
		administrativoForm.setUsername(administrativo.getUserAccount().getUsername());

		return administrativoForm;
	}

	public Actor reconstruct(final AdministrativoForm administrativoForm, final HttpServletRequest request) {
		Actor res;

		if (administrativoForm.getId() == 0) {
			res = this.create();
			
			//Generación de contraseña
			String password = actorService.generateSecureRandomPassword();			
			res.getUserAccount().setPassword(password);
			
			actorService.enviarCredencialesCorreo(administrativoForm.getEmail(), administrativoForm.getUsername(), password, false, request);
		} else {
			res = this.findByPrincipal();
			
			// Comprobacion para que ambas contraseñas sean iguales
			Assert.isTrue(administrativoForm.getPassword().equals(administrativoForm.getPassword2()), "Las contraseñas no son iguales");
			
			Assert.isTrue(res.getId() == (administrativoForm.getId()));
		}

		res.setNombre(administrativoForm.getNombre());
		res.setApellidos(administrativoForm.getApellidos());
		res.setNif(administrativoForm.getNif());
		res.setEmail(administrativoForm.getEmail());

		// res.setPicture(administrativoForm.getPicture());
		
		res.getUserAccount().setUsername(administrativoForm.getUsername());


		return res;
	}
	
	public List<Actor> administrativosFiltrados(final BusquedaAdministrativosForm busqForm){
		String query = "";
		
		query = "SELECT a from Actor a JOIN a.userAccount.authorities auth WHERE auth.authority = 'ADMINISTRATIVO'";
		
		if(!StringUtils.isEmpty(busqForm.getNif())) {
			query += " AND a.nif LIKE '%" + busqForm.getNif() + "%'";
		}
		if(!StringUtils.isEmpty(busqForm.getNombre())) {
			query += " AND a.nombre LIKE '%" + busqForm.getNombre() + "%'";
		}
		if(!StringUtils.isEmpty(busqForm.getApellidos())) {
			query += " AND a.apellidos LIKE '%" + busqForm.getApellidos() + "%'";
		}
		
		if(busqForm.getActivo() != null) {
			if(busqForm.getActivo()) {
				query += " AND a.userAccount.enabled = 1";
			}else {
				query += " AND a.userAccount.enabled = 0";
			}
		}
		
		query += " ORDER BY a.apellidos";	
		
		TypedQuery<Actor> q = em.createQuery(query, Actor.class);
		
		return q.getResultList();
	}

}
