
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Documento extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String titulo;
	private String formato;
	private byte[] archivo;

	// Constructors -----------------------------------------------------------

	public Documento() {
		super();
	}

	// Getters and setters ----------------------------------------------------	

//	@NotNull
//	@NotBlank
//	public String getTitulo() {
//		return titulo;
//	}
//
//	public void setTitulo(final String titulo) {
//		this.titulo = titulo;
//	}

	@NotNull
	@NotBlank
	public String getFormato() {
		return formato;
	}
	
	public void setFormato(final String formato) {
		this.formato = formato;
	}

	@NotNull
	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(final byte[] archivo) {
		this.archivo = archivo;
	}

	// Relationships ------------------------------------------------------------
	private Actor uploader;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getUploader() {
		return this.uploader;
	}

	public void setUploader(final Actor uploader) {
		this.uploader = uploader;
	}

	

}
