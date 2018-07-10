
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Valoracion;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Integer> {

}
