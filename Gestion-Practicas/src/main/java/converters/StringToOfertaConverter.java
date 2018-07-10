package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Oferta;
import repositories.OfertaRepository;

@Component
@Transactional
public class StringToOfertaConverter implements Converter<String, Oferta> {

	@Autowired
	OfertaRepository ofertaRepository;

	@Override
	public Oferta convert(String text) {
		Oferta result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.ofertaRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
