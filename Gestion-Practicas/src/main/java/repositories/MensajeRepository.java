
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {

	// lista de mensajes de una carpeta
	@Query("select m from Mensaje m where m.carpeta.id=?1")
	Collection<Mensaje> findMensajesByCarpeta(int carpetaId);
	
	// Obtiene maximo id de los mensajes
	@Query("SELECT MAX(a.id) FROM Mensaje a")
	Integer maxMensajeId();
	
}
