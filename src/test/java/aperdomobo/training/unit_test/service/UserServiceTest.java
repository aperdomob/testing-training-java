package aperdomobo.training.unit_test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import aperdomobo.training.unit_test.controller.dto.UserDto;
import aperdomobo.training.unit_test.model.User;
import aperdomobo.training.unit_test.repository.UserRepository;

public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private PasswordService passwordService;
	
	@Mock
	private UsernameService usernameService;
	
	@InjectMocks
	private UserService userService = new DefaultUserService();
	
	@Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void findAllTest() {
		List<User> users = new ArrayList<User>();
		users.add(new User("alejandro", "MyPassword", "alejandro@gmail.com"));
		users.add(new User("samuel", "MyOtherPassword", "samuel@gmail.com"));
		
		when(this.userRepository.findAll()).thenReturn(users);
		
		List<UserDto> usersDto = this.userService.findAll();
		
		assertEquals(2, usersDto.size());
		assertEquals("alejandro", usersDto.get(0).getUsername());
		assertEquals("MyPassword", usersDto.get(0).getPassword());
		assertEquals("alejandro@gmail.com", usersDto.get(0).getEmail());
		
		assertEquals("samuel", usersDto.get(1).getUsername());
		assertEquals("MyOtherPassword", usersDto.get(1).getPassword());
		assertEquals("samuel@gmail.com", usersDto.get(1).getEmail());
	}
}
