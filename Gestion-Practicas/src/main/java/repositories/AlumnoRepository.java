
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

	// Obtiene actor que está logado
	@Query("select a from Alumno a where a.userAccount.id = ?1")
	Alumno findByPrincipal(int userAccountId);

	// Obtiene actor por su username
	@Query("select a from Alumno a where a.userAccount.username = ?1")
	Alumno findByUsername(String username);
}
