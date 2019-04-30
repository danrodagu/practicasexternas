
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {	
	
	// Obtiene token por el confirmationToken
	@Query("select t from Token t where t.confirmationToken = ?1")
	Token findTokenByConfirmationToken(String confirmationToken);
	
	// Obtiene maximo id de los tokens
	@Query("SELECT MAX(t.id) FROM Token t")
	Integer maxTokenId();
	
}
