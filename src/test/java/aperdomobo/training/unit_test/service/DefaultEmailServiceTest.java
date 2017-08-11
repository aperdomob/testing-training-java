package aperdomobo.training.unit_test.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import javax.mail.internet.InternetAddress;
import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import aperdomobo.training.unit_test.model.User;
import aperdomobo.training.unit_test.repository.UserRepository;

public class DefaultEmailServiceTest {

	@Mock
	private InternetAddress emailAddress;
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private DefaultEmailService emailService; 

	@Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
        emailService  = new DefaultEmailService(userRepository);
	}
	
	@Test
	public void isValidEmailFormatWithoutUpperTest() {
		boolean isValid = this.emailService.isValidEmailFormat("abcd1234");
		assertFalse(isValid);
	}
	
	@Test
	public void isValidEmailFormatWithoutLowerTest() {
		boolean isValid = this.emailService.isValidEmailFormat("1234ABCD");
		assertFalse(isValid);
	}

	@Test
	public void isValidEmailFormatWithoutNumbersTest() {
		boolean isValid = this.emailService.isValidEmailFormat("ABCDefgh");
		assertFalse(isValid);
	}

	@Test
	public void isValidEmailFormatInEmptyStringTest() {
		boolean isValid = this.emailService.isValidEmailFormat("");
		assertFalse(isValid);
	}

	@Test
	public void isValidEmailFormatWithLessSevenCharactersTest() {
		boolean isValid = this.emailService.isValidEmailFormat("Ab7");
		assertFalse(isValid);
	}

	@Test
	public void isValidEmailFormatWithValidPassowrd() {
		boolean isValid = this.emailService.isValidEmailFormat("aB1cD2Ef3");
		assertTrue(isValid);
	}
	
	@Test
	public void isUsedEmailWhenItIsUsed() {
		Collection<User> users = new ArrayList<User>();
		users.add(new User("aperdomobo", "MyPassword", "aperdomobo@gmail.com"));
		users.add(new User("kenan.samuel", "MyOtherPassword", "kenan.samuel@gmail.com"));
		when(userRepository.findAll()).thenReturn(users);
		
		boolean isUsedEmail = this.emailService.isUsedEmail("aperdomobo@gmail.com");
		
		assertTrue(isUsedEmail);
	}

	@Test
	public void isUsedEmailWhenItIsNotUsed() {
		Collection<User> users = new ArrayList<User>();
		users.add(new User("aperdomobo", "MyPassword", "aperdomobo@gmail.com"));
		users.add(new User("kenan.samuel", "MyOtherPassword", "kenan.samuel@gmail.com"));
		when(userRepository.findAll()).thenReturn(users);
		
		boolean isUsedEmail = this.emailService.isUsedEmail("nonusedemail@gmail.com");
		
		assertFalse(isUsedEmail);
	}
	
	@Test
	public void isValidEmailTest() {
		
	}
}
