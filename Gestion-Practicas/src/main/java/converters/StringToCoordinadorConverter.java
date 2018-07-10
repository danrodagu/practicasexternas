package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Coordinador;
import repositories.CoordinadorRepository;

@Component
@Transactional
public class StringToCoordinadorConverter implements Converter<String, Coordinador> {

	@Autowired
	CoordinadorRepository coordinadorRepository;

	@Override
	public Coordinador convert(String text) {
		Coordinador result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.coordinadorRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
