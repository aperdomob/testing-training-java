package aperdomobo.training.unit_test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import aperdomobo.training.unit_test.controller.dto.UserDto;
import aperdomobo.training.unit_test.service.UserService;

@RestController
@RequestMapping("/testing-training")
public class UserController {

	
	@Autowired
	private UserService userService;

	@RequestMapping("/users")
	public List<UserDto> findAll() {
		return userService.findAll();
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public void save(@RequestBody UserDto request) {
		
	}

}
