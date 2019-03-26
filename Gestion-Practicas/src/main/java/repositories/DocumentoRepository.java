
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

	// Find documento by formato
	@Query("select d from Documento d where d.formato=?1")
	Collection<Documento> findDocumentosByFormato(String formato);
	
	// Find documento by oferta
	@Query("select d from Documento d where d.oferta.id=?1")
	Collection<Documento> findDocumentosByOferta(int ofertaId);
	
	// Obtiene los documentos de la documentación visible por todos
	@Query("select d from Documento d where d.oferta.id IS NULL")
	Collection<Documento> findDocumentosSinOferta();
	
	// Obtiene maximo id de los documentos
	@Query("SELECT MAX(a.id) FROM Documento a")
	Integer maxDocumentoId();
	
}
