
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Carpeta;

@Repository
public interface CarpetaRepository extends JpaRepository<Carpeta, Integer> {

}
