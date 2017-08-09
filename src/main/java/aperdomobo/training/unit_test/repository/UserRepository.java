package aperdomobo.training.unit_test.repository;

import java.util.Collection;

import aperdomobo.training.unit_test.model.User;

public interface UserRepository {
	Collection<User> findAll();
	boolean exists(String username);
	void add(User user);
	void clean();
}
