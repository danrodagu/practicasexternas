package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Carpeta;

@Component
@Transactional
public class CarpetaToStringConverter implements Converter<Carpeta, String> {

	@Override
	public String convert(Carpeta carpeta) {
		String result;
		if (carpeta == null) {
			result = null;
		} else {
			result = String.valueOf(carpeta.getId());
		}
		return result;
	}

}
