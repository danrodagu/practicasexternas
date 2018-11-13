package forms;

import javax.validation.constraints.NotNull;

public class DocumentoForm {

	// Attributes -------------------------------------------------------------

	private Integer id;
	private byte[] archivo;

	// Constructors -----------------------------------------------------------

	public DocumentoForm() {
		super();
		this.id = 0;
	}

	@NotNull
	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(final byte[] archivo) {
		this.archivo = archivo;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

}
