package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Documento;
import repositories.DocumentoRepository;

@Component
@Transactional
public class StringToDocumentoConverter implements Converter<String, Documento> {

	@Autowired
	DocumentoRepository documentoRepository;

	@Override
	public Documento convert(final String text) {
		Documento result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.documentoRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
