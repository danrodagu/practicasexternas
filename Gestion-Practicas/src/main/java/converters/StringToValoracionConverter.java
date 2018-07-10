package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Valoracion;
import repositories.ValoracionRepository;

@Component
@Transactional
public class StringToValoracionConverter implements Converter<String, Valoracion> {

	@Autowired
	ValoracionRepository valoracionRepository;

	@Override
	public Valoracion convert(String text) {
		Valoracion result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.valoracionRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
