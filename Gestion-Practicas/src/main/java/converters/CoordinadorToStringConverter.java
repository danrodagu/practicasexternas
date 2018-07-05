package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Coordinador;

@Component
@Transactional
public class CoordinadorToStringConverter implements Converter<Coordinador, String> {

	@Override
	public String convert(final Coordinador coordinador) {
		String result;

		if (coordinador == null) {
			result = null;
		} else {
			result = String.valueOf(coordinador.getId());
		}

		return result;
	}

}
