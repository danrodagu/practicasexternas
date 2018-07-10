
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Administrativo extends Actor {

	// Constructors -----------------------------------------------------------

	public Administrativo() {
		super();
	}

	// Getters and setters ----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
