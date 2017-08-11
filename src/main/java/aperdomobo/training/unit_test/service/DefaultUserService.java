package aperdomobo.training.unit_test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aperdomobo.training.unit_test.controller.dto.DtoTransformer;
import aperdomobo.training.unit_test.controller.dto.UserDto;
import aperdomobo.training.unit_test.model.User;
import aperdomobo.training.unit_test.repository.UserRepository;

@Service
public class DefaultUserService implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private UsernameService usernameService;

	@Override
	public List<UserDto> findAll() {
		return DtoTransformer.toDto(userRepository.findAll(), UserDto.class);
	}

	@Override
	public void saveUser(User user) throws ApplicationException {
		if (!this.passwordService.isValidPassword(user.getPassword())) {
			throw new ApplicationException("The password is invalid");
		}

		if (!this.emailService.isValidEmailFormat(user.getEmail())) {
			throw new ApplicationException("The email format is invalid");
		}

		if (this.usernameService.isUsedUsername(user.getUsername())) {
			throw new ApplicationException("the username is already used");
		}

		if (this.emailService.isUsedEmail(user.getEmail())) {
			throw new ApplicationException("the email is already used");
		}

		if (!this.emailService.isValidEmail(user.getEmail())) {
			throw new ApplicationException("the email isn't valid");
		}

		userRepository.add(user);
	}
}
