
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Integer> {

}
