package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Mensaje;
import repositories.MensajeRepository;

@Component
@Transactional
public class StringToMensajeConverter implements Converter<String, Mensaje> {

	@Autowired
	MensajeRepository mensajeRepository;

	@Override
	public Mensaje convert(String text) {
		Mensaje result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.mensajeRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
