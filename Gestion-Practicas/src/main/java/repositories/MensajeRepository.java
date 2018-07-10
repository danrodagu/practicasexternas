
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {

}
