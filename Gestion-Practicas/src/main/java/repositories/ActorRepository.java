
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	// Obtiene actor que está logado
	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByPrincipal(int userAccountId);

	// Obtiene actor por su username
	@Query("select a from Actor a where a.userAccount.username = ?1")
	Actor findByUsername(String username);
	
//	--- ALUMNO ---
	
	// Obtiene todos los alumnos
	@Query("select a from Actor a join a.userAccount.authorities auth where auth.authority = 'ALUMNO'")
	Collection<Actor> findAllAlumnos();
	
	
	
//	--- COORDINADOR ---
	
//	--- TUTOR ---
	
//	--- ADMINISTRATIVO ---

}
