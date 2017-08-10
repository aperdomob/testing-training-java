package aperdomobo.training.unit_test.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class DefaultPasswordService implements PasswordService {

	private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)\\w{8,}");

	@Override
	public boolean isValidPassword(String password) {
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
		return matcher.find();
	}
}
