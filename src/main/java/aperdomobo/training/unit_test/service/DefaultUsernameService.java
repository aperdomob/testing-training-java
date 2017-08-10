package aperdomobo.training.unit_test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aperdomobo.training.unit_test.repository.UserRepository;

@Service
public class DefaultUsernameService implements UsernameService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean isUsedUsername(String username) {
		return userRepository.exists(username);
	}
}
