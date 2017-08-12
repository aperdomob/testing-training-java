package aperdomobo.training.unit_test.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import aperdomobo.training.unit_test.model.User;

@Component
public class DefaultUserRepository implements UserRepository {

	private final Map<String, User> userByUsername;
	
	public DefaultUserRepository() {
		this(new HashMap<String, User>());
	}
	
	public DefaultUserRepository(Map<String, User> userByUsername) {
		this.userByUsername = userByUsername;
	}
	
	@Override
	public Collection<User> findAll() {
		return this.userByUsername.values();
	}

	@Override
	public void add(User user) {
		userByUsername.put(user.getUsername(), user);
	}

	@Override
	public void clean() {
		this.userByUsername.clear();
	}
	
	@Override
	public boolean exists(String username) {
		return this.userByUsername.containsKey(username);
	}

}
