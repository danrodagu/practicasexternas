package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Valoracion;

@Component
@Transactional
public class ValoracionToStringConverter implements Converter<Valoracion, String> {

	@Override
	public String convert(final Valoracion valoracion) {
		String result;

		if (valoracion == null) {
			result = null;
		} else {
			result = String.valueOf(valoracion.getId());
		}

		return result;
	}

}
