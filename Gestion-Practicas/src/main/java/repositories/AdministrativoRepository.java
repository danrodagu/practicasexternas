
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Administrativo;

@Repository
public interface AdministrativoRepository extends JpaRepository<Administrativo, Integer> {

}
