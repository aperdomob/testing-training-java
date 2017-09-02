package aperdomobo.training.unit_test.repository;

import java.util.Collection;

import aperdomobo.training.unit_test.model.User;
import com.google.firebase.database.ValueEventListener;

public interface UserRepository extends ValueEventListener {
	Collection<User> findAll();
	boolean exists(String username);
	void add(User user);
}
