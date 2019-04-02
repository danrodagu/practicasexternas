package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Noticia;

@Component
@Transactional
public class NoticiaToStringConverter implements Converter<Noticia, String> {

	@Override
	public String convert(final Noticia noticia) {
		String result;

		if (noticia == null) {
			result = null;
		} else {
			result = String.valueOf(noticia.getId());
		}

		return result;
	}

}
