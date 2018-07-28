
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Coordinador;

@Repository
public interface CoordinadorRepository extends JpaRepository<Coordinador, Integer> {

	// Obtiene actor que está logado
	@Query("select a from Coordinador a where a.userAccount.id = ?1")
	Coordinador findByPrincipal(int userAccountId);

	// Obtiene actor por su username
	@Query("select a from Coordinador a where a.userAccount.username = ?1")
	Coordinador findByUsername(String username);

}
