
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Tutor extends Actor {

	// Constructors -----------------------------------------------------------

	public Tutor() {
		super();
	}

	// Getters and setters ----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
