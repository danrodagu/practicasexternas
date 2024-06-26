/* Authority.java

 * 
 */

package security;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;

@Embeddable
@Access(AccessType.PROPERTY)
public class Authority implements GrantedAuthority {

	// Constructors -----------------------------------------------------------

	private static final long serialVersionUID = 1L;

	public Authority() {
		super();
	}

	// Values -----------------------------------------------------------------

	public static final String ALUMNO = "ALUMNO";
	public static final String COORDINADOR = "COORDINADOR";
	public static final String TUTOR = "TUTOR";
	public static final String ADMINISTRATIVO = "ADMINISTRATIVO";

	// Attributes -------------------------------------------------------------

	private String authority;

	@NotBlank
	@Pattern(regexp = "^" + ALUMNO + "|" + COORDINADOR + "|" + TUTOR + "|" + ADMINISTRATIVO + "$")
	@Override
	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	public static Collection<Authority> listAuthorities() {
		Collection<Authority> result;
		Authority authority;

		result = new ArrayList<Authority>();

		authority = new Authority();
		authority.setAuthority(ALUMNO);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(COORDINADOR);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(TUTOR);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(ADMINISTRATIVO);
		result.add(authority);

		return result;
	}

	// Equality ---------------------------------------------------------------

	@Override
	public int hashCode() {
		return this.getAuthority().hashCode();
	}

	@Override
	public boolean equals(final Object other) {
		boolean result;

		if (this == other) {
			result = true;
		} else if (other == null) {
			result = false;
		} else if (!this.getClass().isInstance(other)) {
			result = false;
		} else {
			result = (this.getAuthority().equals(((Authority) other).getAuthority()));
		}

		return result;
	}

}
