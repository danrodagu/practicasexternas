
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Coordinador;

@Repository
public interface CoordinadorRepository extends JpaRepository<Coordinador, Integer> {

}
