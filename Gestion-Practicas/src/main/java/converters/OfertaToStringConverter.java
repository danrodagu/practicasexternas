package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Oferta;

@Component
@Transactional
public class OfertaToStringConverter implements Converter<Oferta, String> {

	@Override
	public String convert(final Oferta oferta) {
		String result;

		if (oferta == null) {
			result = null;
		} else {
			result = String.valueOf(oferta.getId());
		}

		return result;
	}

}
