
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Noticia;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Integer> {
	
	// Obtiene todas las noticias ordenadas por fecha más reciente
	@Query("SELECT a FROM Noticia a ORDER BY a.fechaModificacion DESC")
	Collection<Noticia> findAllOrderedByDate();
	
	// Obtiene maximo id de los noticias
	@Query("SELECT MAX(a.id) FROM Noticia a")
	Integer maxNoticiaId();
	
}
