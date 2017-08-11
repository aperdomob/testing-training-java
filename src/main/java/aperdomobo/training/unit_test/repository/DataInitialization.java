package aperdomobo.training.unit_test.repository;

import java.text.ParseException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aperdomobo.training.unit_test.model.User;

@Component
public class DataInitialization {
	private final UserRepository userRepository;
	
	@Autowired
	public DataInitialization(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@PostConstruct
	public void init() throws ParseException {
		User user1 = new User("aperdomob", "MiClave123Colombia", "aperdomobo@gmail.com");
		User user2 = new User("kenan.samuel", "K3n4nS4mu3l", "kenan-samuel@gmail.com");
		
		userRepository.add(user1);
		userRepository.add(user2);
	}
}
