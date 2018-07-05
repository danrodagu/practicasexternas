package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tutor;

@Component
@Transactional
public class TutorToStringConverter implements Converter<Tutor, String> {

	@Override
	public String convert(final Tutor tutor) {
		String result;

		if (tutor == null) {
			result = null;
		} else {
			result = String.valueOf(tutor.getId());
		}

		return result;
	}

}
