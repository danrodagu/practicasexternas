
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Integer> {

	// Obtiene actor que está logado
	@Query("select a from Tutor a where a.userAccount.id = ?1")
	Tutor findByPrincipal(int userAccountId);

	// Obtiene actor por su username
	@Query("select a from Tutor a where a.userAccount.username = ?1")
	Tutor findByUsername(String username);
}
