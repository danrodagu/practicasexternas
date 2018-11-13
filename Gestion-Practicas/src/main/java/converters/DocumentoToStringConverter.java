package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Documento;

@Component
@Transactional
public class DocumentoToStringConverter implements Converter<Documento, String> {

	@Override
	public String convert(final Documento documento) {
		String result;

		if (documento == null) {
			result = null;
		} else {
			result = String.valueOf(documento.getId());
		}

		return result;
	}

}
