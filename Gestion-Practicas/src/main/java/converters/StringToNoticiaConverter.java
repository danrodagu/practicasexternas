package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Noticia;
import repositories.NoticiaRepository;

@Component
@Transactional
public class StringToNoticiaConverter implements Converter<String, Noticia> {

	@Autowired
	NoticiaRepository noticiaRepository;

	@Override
	public Noticia convert(final String text) {
		Noticia result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.noticiaRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
