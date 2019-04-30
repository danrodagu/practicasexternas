
package domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Token extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String confirmationToken;
	private Date fechaCreacion;

	// Constructors -----------------------------------------------------------

	public Token() {
		super();
		this.fechaCreacion = new Date();
        this.confirmationToken = UUID.randomUUID().toString();
	}

	// Getters and setters ----------------------------------------------------	


	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(final String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(final Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
