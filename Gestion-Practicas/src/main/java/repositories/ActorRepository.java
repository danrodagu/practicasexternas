
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
	
	// Obtiene maximo id de los actores
	@Query("SELECT MAX(a.id) FROM Actor a")
	Integer maxActorId();
	
	// Comprueba si existe una contraseña en el sistema
	@Query("SELECT COUNT(u) FROM UserAccount u WHERE u.password = ?1")
	Integer passwordsIguales(String password);
	
//	--- ALUMNO ---
	
	// Obtiene todos los alumnos
	@Query("select a from Actor a join a.userAccount.authorities auth where auth.authority = 'ALUMNO'")
	Collection<Actor> findAllAlumnos();
	
	
	
//	--- COORDINADOR ---
	
	// Obtiene todos los coordinadores
	@Query("select a from Actor a join a.userAccount.authorities auth where auth.authority = 'COORDINADOR'")
	Collection<Actor> findAllCoordinadores();
	
//	--- TUTOR ---
	
	// Obtiene todos los tutores
	@Query("select a from Actor a join a.userAccount.authorities auth where auth.authority = 'TUTOR' OR auth.authority = 'COORDINADOR'")
	Collection<Actor> findAllTutores();
	
	// Obtiene los alumnos del tutor logueado
	@Query("select o.alumnoAsignado from Oferta o WHERE o.tutorAsignado.id = ?1")
	Collection<Actor> findMyStudents(int tutorId);
	
//	--- ADMINISTRATIVO ---
	
	// Obtiene todos los administrativos
	@Query("select a from Actor a join a.userAccount.authorities auth where auth.authority = 'ADMINISTRATIVO'")
	Collection<Actor> findAllAdministrativos();

}
