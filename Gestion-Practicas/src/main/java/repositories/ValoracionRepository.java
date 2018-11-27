
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Valoracion;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Integer> {

	// Obtiene maximo id de las valoraciones
	@Query("SELECT MAX(a.id) FROM Valoracion a")
	Integer maxValoracionId();
	
}
