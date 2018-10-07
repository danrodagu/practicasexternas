
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Carpeta;

@Repository
public interface CarpetaRepository extends JpaRepository<Carpeta, Integer> {

	// Find carpeta by actor.
	@Query("select c from Carpeta c where c.actor.id=?1")
	Collection<Carpeta> findCarpetasByActor(int actorId);

	// Find carpeta by nombre and actor id
	@Query("select c from Carpeta c where c.nombre = ?1 AND c.actor.id = ?2")
	Carpeta findCarpetaByNombreAndActor(String nombre, int actorId);
	
}
