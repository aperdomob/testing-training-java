package aperdomobo.training.unit_test.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import aperdomobo.training.unit_test.model.User;

@Component
public class DefaultUserRepository implements UserRepository {

	private Map<String, User> userByUsername;
	
	@Override
	public Collection<User> findAll() {
		return this.userByUsername.values();
	}

	@Override
	public void add(User user) {
		if (this.userByUsername == null) {
			this.userByUsername = new HashMap<String, User>();
		}
		
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
