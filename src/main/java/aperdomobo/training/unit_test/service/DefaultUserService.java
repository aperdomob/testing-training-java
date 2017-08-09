package aperdomobo.training.unit_test.service;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aperdomobo.training.unit_test.controller.dto.DtoTransformer;
import aperdomobo.training.unit_test.controller.dto.UserDto;
import aperdomobo.training.unit_test.model.User;
import aperdomobo.training.unit_test.repository.UserRepository;

@Service
public class DefaultUserService implements UserService {

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)\\w{8,}");

	@Autowired
	private UserRepository userRepository;

	@Override
	public void saveUser(User user) throws ApplicationException {
		if (this.isValidPassword(user.getPassword())) {
			throw new ApplicationException("The password is invalid");
		}

		if (!this.isValidEmailFormat(user.getEmail())) {
			throw new ApplicationException("The email format is invalid");
		}

		if (this.isUsedUsername(user.getUsername())) {
			throw new ApplicationException("the username is already used");
		}

		if (this.isUsedEmail(user.getEmail())) {
			throw new ApplicationException("the email is already used");
		}

		if (this.isValidEmail(user.getEmail())) {
			throw new ApplicationException("the email isn't valid");
		}

		userRepository.add(user);
	}

	private boolean isValidEmail(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	private boolean isUsedEmail(String email) {
		Collection<User> users = userRepository.findAll();
		return users.stream().anyMatch(userEmail -> email.equals(userEmail));
	}

	private boolean isValidEmailFormat(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	private boolean isValidPassword(String password) {
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
		return matcher.find();
	}

	@Override
	public List<UserDto> findAll() {
		return DtoTransformer.toDto(userRepository.findAll(), UserDto.class);
	}

	private boolean isUsedUsername(String username) {
		return userRepository.exists(username);
	}

}
