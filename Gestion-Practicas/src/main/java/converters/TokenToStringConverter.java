package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Token;

@Component
@Transactional
public class TokenToStringConverter implements Converter<Token, String> {

	@Override
	public String convert(final Token token) {
		String result;

		if (token == null) {
			result = null;
		} else {
			result = String.valueOf(token.getId());
		}

		return result;
	}

}
