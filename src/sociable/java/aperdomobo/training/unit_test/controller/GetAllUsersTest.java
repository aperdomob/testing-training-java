package aperdomobo.training.unit_test.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import aperdomobo.training.unit_test.controller.dto.UserDto;
import aperdomobo.training.unit_test.model.User;
import aperdomobo.training.unit_test.repository.DefaultUserRepository;
import aperdomobo.training.unit_test.repository.UserRepository;
import aperdomobo.training.unit_test.service.DefaultEmailService;
import aperdomobo.training.unit_test.service.DefaultPasswordService;
import aperdomobo.training.unit_test.service.DefaultUserService;
import aperdomobo.training.unit_test.service.DefaultUsernameService;
import aperdomobo.training.unit_test.service.EmailService;
import aperdomobo.training.unit_test.service.PasswordService;
import aperdomobo.training.unit_test.service.UserService;
import aperdomobo.training.unit_test.service.UsernameService;

public class GetAllUsersTest {
	private UserController entityToTest;
	
	private UserService userService;
	private EmailService emailService;
	private PasswordService passwordService;
	private UsernameService usernameService;

	@Mock
	private Map<String, User> userByUsername;

	@InjectMocks
	private UserRepository userRepository = new DefaultUserRepository();

	@Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
        
        this.emailService = new DefaultEmailService(this.userRepository);
        this.passwordService = new DefaultPasswordService();
        this.usernameService = new DefaultUsernameService(this.userRepository);
        this.userService = new DefaultUserService(this.userRepository, this.emailService, this.passwordService, usernameService);

        this.entityToTest = new UserController(userService);
    }
	
	@Test
	public void getAllUsers() {
		Collection<User> users = new ArrayList<User>();
		users.add(new User("alejandro", "MyPassword", "alejandro@gmail.com"));
		users.add(new User("samuel", "MyOtherPassword", "samuel@gmail.com"));
		
		when(this.userByUsername.values()).thenReturn(users);
		
		List<UserDto> usersDto = this.entityToTest.findAll();

		assertEquals(2, usersDto.size());
		assertEquals("alejandro", usersDto.get(0).getUsername());
		assertEquals("MyPassword", usersDto.get(0).getPassword());
		assertEquals("alejandro@gmail.com", usersDto.get(0).getEmail());
		
		assertEquals("samuel", usersDto.get(1).getUsername());
		assertEquals("MyOtherPassword", usersDto.get(1).getPassword());
		assertEquals("samuel@gmail.com", usersDto.get(1).getEmail());
	}
}
