package aperdomobo.training.unit_test.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import aperdomobo.training.unit_test.controller.dto.UserDto;
import aperdomobo.training.unit_test.model.User;
import aperdomobo.training.unit_test.repository.DefaultUserRepository;
import aperdomobo.training.unit_test.repository.UserRepository;
import aperdomobo.training.unit_test.service.ApplicationException;
import aperdomobo.training.unit_test.service.DefaultEmailService;
import aperdomobo.training.unit_test.service.DefaultPasswordService;
import aperdomobo.training.unit_test.service.DefaultUserService;
import aperdomobo.training.unit_test.service.DefaultUsernameService;
import aperdomobo.training.unit_test.service.EmailService;
import aperdomobo.training.unit_test.service.PasswordService;
import aperdomobo.training.unit_test.service.UserService;
import aperdomobo.training.unit_test.service.UsernameService;

public class CreateUserTest {
	private UserController entityToTest;
	
	private UserService userService;
	private UserRepository userRepository;
	private EmailService emailService;
	private PasswordService passwordService;
	private UsernameService usernameService;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	private Map<String, User> userByUsername;
	
	@Mock
	private InternetAddress emailAddress;
	
	@Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
        
        this.userRepository = new DefaultUserRepository(this.userByUsername);
        this.emailService = new DefaultEmailService(this.userRepository, this.emailAddress);
        this.passwordService = new DefaultPasswordService();
        this.usernameService = new DefaultUsernameService(this.userRepository);
        this.userService = new DefaultUserService(this.userRepository, this.emailService, this.passwordService, usernameService);

        this.entityToTest = new UserController(userService);
    }

	
	@Test 
	public void createUser() throws ApplicationException {
		UserDto userDto = new UserDto();
		userDto.setEmail("aperdomobo@gmail.com");
		userDto.setPassword("abcDEF12");
		userDto.setUsername("aperdomobo");
		
		this.entityToTest.save(userDto);
		
		User user = new User("aperdomobo", "abcDEF12", "aperdomobo@gmail.com");
		verify(this.userByUsername).put("aperdomobo", user);
	}

	@Test 
	public void createUserWithPasswordWithSubDomain() throws ApplicationException {
		UserDto userDto = new UserDto();
		userDto.setEmail("aperdomobo@gmail.com.co");
		userDto.setPassword("abcDEF12");
		userDto.setUsername("aperdomobo");
		
		this.entityToTest.save(userDto);
		
		User user = new User("aperdomobo", "abcDEF12", "aperdomobo@gmail.com.co");
		verify(this.userByUsername).put("aperdomobo", user);
	}

	@Test
	public void passwordWithoutNumbersTest() throws ApplicationException {
		expectedException.expect(ApplicationException.class);
		expectedException.expectMessage("The password is invalid");
		
		User user = new User("aperdomobo", "MyPassword", "aperdomobo@gmail.com");
		this.userService.saveUser(user);
	}

	@Test
	public void passwordWithoutUppersTest() throws ApplicationException {
		expectedException.expect(ApplicationException.class);
		expectedException.expectMessage("The password is invalid");
		
		User user = new User("aperdomobo", "myp4ssw0rd", "aperdomobo@gmail.com");
		this.userService.saveUser(user);
	}

	@Test
	public void passwordWithoutLowersTest() throws ApplicationException {
		expectedException.expect(ApplicationException.class);
		expectedException.expectMessage("The password is invalid");
		
		User user = new User("aperdomobo", "MYP4SSW0RD", "aperdomobo@gmail.com");
		this.userService.saveUser(user);
	}

	@Test
	public void passwordWithSevenCharatersTest() throws ApplicationException {
		expectedException.expect(ApplicationException.class);
		expectedException.expectMessage("The password is invalid");
		
		User user = new User("aperdomobo", "MyP4ssw", "aperdomobo@gmail.com");
		this.userService.saveUser(user);
	}

	@Test
	public void emailWithoutPreffixTest() throws ApplicationException {
		expectedException.expect(ApplicationException.class);
		expectedException.expectMessage("The email format is invalid");
		
		User user = new User("aperdomobo", "MyP4ssw0rd", "@gmail.com");
		this.userService.saveUser(user);
	}

	@Test
	public void emailWithoutDomainTest() throws ApplicationException {
		expectedException.expect(ApplicationException.class);
		expectedException.expectMessage("The email format is invalid");
		
		User user = new User("aperdomobo", "MyP4ssw0rd", "aperdomobo@.com");
		this.userService.saveUser(user);
	}

	@Test
	public void emailWithoutExtensionTest() throws ApplicationException {
		expectedException.expect(ApplicationException.class);
		expectedException.expectMessage("The email format is invalid");
		
		User user = new User("aperdomobo", "MyP4ssw0rd", "aperdomobo@gmail");
		this.userService.saveUser(user);
	}

	@Test
	public void usernameIsAlreadyUsedTest() throws ApplicationException {
		expectedException.expect(ApplicationException.class);
		expectedException.expectMessage("the username is already used");
		
		when(this.userByUsername.containsKey("aperdomobo")).thenReturn(true);
		
		User user = new User("aperdomobo", "MyP4ssw0rd", "aperdomobo@gmail.com");
		this.userService.saveUser(user);
	}

	@Test
	public void emailIsAlreadyUsedTest() throws ApplicationException {
		expectedException.expect(ApplicationException.class);
		expectedException.expectMessage("the email is already used");
		
		Collection<User> users = new ArrayList<User>();
		users.add(new User("aperdomobo", "MyPassword", "aperdomobo@gmail.com"));
		users.add(new User("kenan.samuel", "MyOtherPassword", "kenan.samuel@gmail.com"));
		
		when(this.userByUsername.values()).thenReturn(users);
		
		User user = new User("aperdomobo", "MyP4ssw0rd", "aperdomobo@gmail.com");
		this.userService.saveUser(user);
	}
	
	@Test
	public void emailIsNotValidMessageTest() throws ApplicationException, AddressException {
		expectedException.expect(ApplicationException.class);
		expectedException.expectMessage("the email isn't valid");
		
		doThrow(AddressException.class).when(this.emailAddress).validate();
		
		User user = new User("aperdomobo", "MyP4ssw0rd", "aperdomobo@gmail.com");
		this.userService.saveUser(user);
	}
}
