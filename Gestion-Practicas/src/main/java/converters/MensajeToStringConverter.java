package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Mensaje;

@Component
@Transactional
public class MensajeToStringConverter implements Converter<Mensaje, String> {

	@Override
	public String convert(final Mensaje mensaje) {
		String result;

		if (mensaje == null) {
			result = null;
		} else {
			result = String.valueOf(mensaje.getId());
		}

		return result;
	}

}
