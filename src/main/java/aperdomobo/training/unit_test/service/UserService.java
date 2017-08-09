package aperdomobo.training.unit_test.service;

import java.util.List;

import aperdomobo.training.unit_test.controller.dto.UserDto;
import aperdomobo.training.unit_test.model.User;

public interface UserService {
	List<UserDto> findAll();
	void saveUser(User user) throws ApplicationException;
}
