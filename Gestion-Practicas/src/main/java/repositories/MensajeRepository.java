
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {

	// lista de mensajes de una carpeta
	@Query("select m from Mensaje m where m.carpeta.id=?1 ORDER BY m.fecha DESC ")
	Collection<Mensaje> findMensajesByCarpeta(int carpetaId);
	
	// Obtiene numero de mensajes no leidos por actor(carpeta 'recibido')
	@Query("SELECT COUNT(m) FROM Mensaje m WHERE m.carpeta.id = ?1 AND m.leido = 0")
	Integer numMsgNoLeidos(int carpetaId);
	
	// Obtiene maximo id de los mensajes
	@Query("SELECT MAX(a.id) FROM Mensaje a")
	Integer maxMensajeId();
	
}
