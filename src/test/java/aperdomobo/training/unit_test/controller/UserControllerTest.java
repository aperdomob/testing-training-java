package aperdomobo.training.unit_test.controller;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import aperdomobo.training.unit_test.controller.dto.UserDto;
import aperdomobo.training.unit_test.model.User;
import aperdomobo.training.unit_test.service.ApplicationException;
import aperdomobo.training.unit_test.service.UserService;

public class UserControllerTest {

	@Mock
	private UserService userService;

	private UserController controller;

	@Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
        this.controller = new UserController(userService);
    }
	
	@Test
	public void getAll() {
		controller.findAll();
		verify(userService).findAll();
	}
	
	@Test
	public void saveTest() throws ApplicationException {
		UserDto userDto = new UserDto();
		userDto.setEmail("aperdomobo@gmail.com");
		userDto.setPassword("MyPassword");
		userDto.setUsername("aperdomobo");
		
		User user = new User("aperdomobo", "MyPassword", "aperdomobo@gmail.com");
		
		controller.save(userDto);
		
		verify(userService).saveUser(user);
	}
}
