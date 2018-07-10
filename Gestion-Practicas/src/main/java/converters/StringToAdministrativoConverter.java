package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Administrativo;
import repositories.AdministrativoRepository;

@Component
@Transactional
public class StringToAdministrativoConverter implements Converter<String, Administrativo> {

	@Autowired
	AdministrativoRepository administrativoRepository;

	@Override
	public Administrativo convert(String text) {
		Administrativo result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.administrativoRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
