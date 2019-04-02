
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Noticia;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Integer> {
	
	// Obtiene maximo id de los noticias
	@Query("SELECT MAX(a.id) FROM Noticia a")
	Integer maxNoticiaId();
	
}
