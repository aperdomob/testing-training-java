package aperdomobo.training.unit_test.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aperdomobo.training.unit_test.repository.UserRepository;

@Service
public class DefaultUsernameService implements UsernameService{
	private final UserRepository userRepository;
	
	@Autowired
	public DefaultUsernameService(UserRepository userRepository) {
		this.userRepository = Objects.requireNonNull(userRepository);
	}

	@Override
	public boolean isUsedUsername(String username) {
		return userRepository.exists(username);
	}
}
