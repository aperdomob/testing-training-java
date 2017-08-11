package aperdomobo.training.unit_test.service;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aperdomobo.training.unit_test.model.User;
import aperdomobo.training.unit_test.repository.UserRepository;

@Service
public class DefaultEmailService implements EmailService {

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	// @Autowired
	// private InternetAddress emailAddress;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	@Override
	public boolean isValidEmailFormat(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	@Override
	public boolean isValidEmail(String email) {
//		this.emailAddress.setAddress(email);
//		boolean result = true;
//		
//		try {
//			InternetAddress emailAddr = new InternetAddress(email);
//			emailAddr.validate();
//		} catch (AddressException ex) {
//			result = false;
//		}
//		
//		return result;
		
		return true;
	}

	@Override
	public boolean isUsedEmail(String email) {
		Collection<User> users = userRepository.findAll();
		return users.stream().anyMatch(userEmail -> email.equals(userEmail));
	}

}
