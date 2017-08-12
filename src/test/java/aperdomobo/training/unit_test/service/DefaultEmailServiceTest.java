package aperdomobo.training.unit_test.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

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
        emailService  = new DefaultEmailService(userRepository, emailAddress);
	}
	
	@Test
	public void isValidEmailFormatWithoutAtTest() {
		boolean isValid = this.emailService.isValidEmailFormat("aperdomobogmail.com");
		assertFalse(isValid);
	}
	
	@Test
	public void isValidEmailFormatWithoutAccountTest() {
		boolean isValid = this.emailService.isValidEmailFormat("@gmail.com");
		assertFalse(isValid);
	}

	@Test
	public void isValidEmailFormatWithoutDomainTest() {
		boolean isValid = this.emailService.isValidEmailFormat("aperdomobo@.com");
		assertFalse(isValid);
	}

	@Test
	public void isValidEmailFormatWithoutExtensionTest() {
		boolean isValid = this.emailService.isValidEmailFormat("aperdomobo@gmail");
		assertFalse(isValid);
	}

	@Test
	public void isValidEmailFormatWithEmptyStringTest() {
		boolean isValid = this.emailService.isValidEmailFormat("");
		assertFalse(isValid);
	}
	
	@Test
	public void isValidEmailFormatTest() {
		boolean isValid = this.emailService.isValidEmailFormat("aperdomobo@gmail.com");
		assertTrue(isValid);
	}

	@Test
	public void isValidEmailFormatWithSubdomainTest() {
		boolean isValid = this.emailService.isValidEmailFormat("aperdomobo@gmail.com.co");
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
	public void isValidEmailTest() throws AddressException {
		boolean isValidEmail = this.emailService.isValidEmail("aperdomobo@gmail.com");
		
		assertTrue(isValidEmail);
		verify(this.emailAddress).setAddress("aperdomobo@gmail.com");
		verify(this.emailAddress).validate();
	}
	
	@Test
	public void isNotValidEmailTest() throws AddressException {
		doThrow(AddressException.class).when(this.emailAddress).validate();
		
		boolean isValidEmail = this.emailService.isValidEmail("aperdomobo@gmail.com");
		
		assertFalse(isValidEmail);
	}
}
