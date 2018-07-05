package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Administrativo;

@Component
@Transactional
public class AdministrativoToStringConverter implements Converter<Administrativo, String> {

	@Override
	public String convert(Administrativo administrativo) {
		String result;

		if (administrativo == null) {
			result = null;
		} else {
			result = String.valueOf(administrativo.getId());
		}

		return result;
	}

}
