
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Integer> {

}
