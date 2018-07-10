package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tutor;
import repositories.TutorRepository;

@Component
@Transactional
public class StringToTutorConverter implements Converter<String, Tutor> {

	@Autowired
	TutorRepository tutorRepository;

	@Override
	public Tutor convert(String text) {
		Tutor result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.tutorRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
