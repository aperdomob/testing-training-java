package aperdomobo.training.unit_test.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import aperdomobo.training.unit_test.controller.dto.DtoTransformer;
import aperdomobo.training.unit_test.controller.dto.UserDto;
import aperdomobo.training.unit_test.model.User;
import aperdomobo.training.unit_test.service.ApplicationException;
import aperdomobo.training.unit_test.service.UserService;

@RestController
@RequestMapping("/testing-training")
public class UserController {

	private final UserService userService;
	
	@Autowired
	public UserController(final UserService userService) {
		this.userService = Objects.requireNonNull(userService);
	}
		
	@RequestMapping("/users")
	public List<UserDto> findAll() {
		return userService.findAll();
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public void save(@RequestBody UserDto request) throws ApplicationException {
		User user = DtoTransformer.toDto(request, User.class);
		userService.saveUser(user);
	}

}
