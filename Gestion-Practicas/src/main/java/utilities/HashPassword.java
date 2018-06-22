/*
 * HashPassword.java

 */

package utilities;

import java.io.IOException;

//import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import utilities.internal.ConsoleReader;

public class HashPassword {

	public static void main(final String[] args) throws IOException {
		// Md5PasswordEncoder encoder;
		BCryptPasswordEncoder encoder;
		ConsoleReader reader;
		String line, hash;

		try {
			System.out.printf("HashPassword 1.9%n");
			System.out.printf("----------------%n%n");

			// encoder = new Md5PasswordEncoder();
			encoder = new BCryptPasswordEncoder();
			reader = new ConsoleReader();

			line = reader.readLine();
			while (!line.equals("quit")) {
				// hash = encoder.encodePassword(line, null);
				hash = encoder.encode(line);
				System.out.println(hash);
				line = reader.readLine();
			}
		} catch (final Throwable oops) {
			System.out.flush();
			System.err.printf("%n%s%n", oops.getLocalizedMessage());
			// oops.printStackTrace(System.out);
		}
	}

}
