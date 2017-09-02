package aperdomobo.training.unit_test.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import aperdomobo.training.unit_test.model.User;

@Component
public class DefaultUserRepository implements UserRepository {

  private static final Logger logger = LoggerFactory.getLogger(DefaultUserRepository.class);

  private Map<String, User> userByUsername;
  private DatabaseReference reference;

  public DefaultUserRepository() {
    this(FirebaseDatabase.getInstance());
  }

  public DefaultUserRepository(FirebaseDatabase database) {
    this.userByUsername = new HashMap<>();

    this.reference = database.getReference();

    reference.child("users").addValueEventListener(this);
  }
  
  @Override
  public Collection<User> findAll() {
    return this.userByUsername.values();
  }

  @Override
  public void add(User user) {
    reference.child("users").child(user.getUsername()).setValue(user);
  }

  @Override
  public boolean exists(String username) {
    return this.userByUsername.containsKey(username);
  }

  @Override
  public void onCancelled(DatabaseError databaseError) {
    logger.error("loadPost:onCancelled", databaseError.toException());
  }

  @Override
  public void onDataChange(DataSnapshot dataSnapshot) {
    for (DataSnapshot children: dataSnapshot.getChildren()) {
      User user = children.getValue(User.class);
      this.userByUsername.put(user.getUsername(), user);
    }
  }
}
