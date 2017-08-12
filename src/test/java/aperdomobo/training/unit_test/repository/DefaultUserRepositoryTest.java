package aperdomobo.training.unit_test.repository;

import static org.mockito.Mockito.verify;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import aperdomobo.training.unit_test.model.User;

public class DefaultUserRepositoryTest {

	@Mock
	private Map<String, User> userByUsername;
	
	private UserRepository userRepository;
	
	@Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        userRepository = new DefaultUserRepository(userByUsername);
	}
	
	@Test
	public void findAllTest() {
		userRepository.findAll();
		verify(userByUsername).values();
	}

	@Test
	public void addTest() {
		User user = new User("aperdomobo", "MyPassword", "aperdomobo@gmail.com");
		userRepository.add(user);
		
		verify(userByUsername).put("aperdomobo", user);
	}
	
	@Test
	public void cleanTest() {
		userRepository.clean();
		verify(userByUsername).clear();
	}
	
	@Test
	public void exists() {
		userRepository.exists("aperdomobo");
		verify(userByUsername).containsKey("aperdomobo");
	}
}
