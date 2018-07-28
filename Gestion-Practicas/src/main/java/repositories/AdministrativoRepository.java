
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrativo;

@Repository
public interface AdministrativoRepository extends JpaRepository<Administrativo, Integer> {

	// Obtiene actor que está logado
	@Query("select a from Administrativo a where a.userAccount.id = ?1")
	Administrativo findByPrincipal(int userAccountId);

	// Obtiene actor por su username
	@Query("select a from Administrativo a where a.userAccount.username = ?1")
	Administrativo findByUsername(String username);
}
