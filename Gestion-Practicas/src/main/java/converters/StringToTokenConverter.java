package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Token;
import repositories.TokenRepository;

@Component
@Transactional
public class StringToTokenConverter implements Converter<String, Token> {

	@Autowired
	TokenRepository tokenRepository;

	@Override
	public Token convert(final String text) {
		Token result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.tokenRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
