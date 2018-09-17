
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Integer> {

	// Obtiene oferta por empresa
	@Query("select o from Oferta o where o.empresa = ?1")
	Collection<Oferta> ofertasByEmpresa(String empresa);
	
	// Obtiene oferta por pais
	@Query("select o from Oferta o where o.pais = ?1")
	Collection<Oferta> ofertasByPais(String pais);
	
}
