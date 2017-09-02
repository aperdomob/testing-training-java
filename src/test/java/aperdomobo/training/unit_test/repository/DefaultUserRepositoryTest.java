package aperdomobo.training.unit_test.repository;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.junit.Before;
import org.junit.Test;

import aperdomobo.training.unit_test.model.User;

public class DefaultUserRepositoryTest {

	private DatabaseReference reference;
	private DatabaseReference usersReference;
	private UserRepository userRepository;
	
	@Before
	public void initMocks() {
		FirebaseDatabase database = mock(FirebaseDatabase.class);

		this.reference = mock(DatabaseReference.class);
		this.usersReference = mock(DatabaseReference.class);

		when(database.getReference()).thenReturn(this.reference);
		when(this.reference.child("users")).thenReturn(this.usersReference);

		this.userRepository = new DefaultUserRepository(database);
	}
	
	@Test
	public void findAllTest() {
		User firstUser = new User();
		firstUser.setUsername("aperdomobo");
		firstUser.setEmail(("aperdomobo@gmail.com"));
		firstUser.setPassword("abcDEF123");

		User secondUser = new User();
		secondUser.setUsername("kenansamuel");
		secondUser.setPassword("1aB2cD3eF");
		secondUser.setEmail("kenan-samuel@gmail.com");

		DataSnapshot dataSnapshot = mock(DataSnapshot.class);
		List<DataSnapshot> childrens = new ArrayList<DataSnapshot>();

		DataSnapshot firstChildren = mock(DataSnapshot.class);
		when(firstChildren.getValue(User.class)).thenReturn(firstUser);

		DataSnapshot secondChildren = mock(DataSnapshot.class);
		when(secondChildren.getValue(User.class)).thenReturn(secondUser);

		childrens.add(firstChildren);
		childrens.add(secondChildren);

		when(dataSnapshot.getChildren()).thenReturn(childrens);

		userRepository.onDataChange(dataSnapshot);
		List<User> allUsers = new ArrayList<>();
		allUsers.addAll(userRepository.findAll());

		assertEquals(2,  allUsers.size());
		assertEquals(secondUser, allUsers.get(0));
		assertEquals(firstUser, allUsers.get(1));
	}

	@Test
	public void addTest() {
		User user = new User("aperdomobo", "MyPassword", "aperdomobo@gmail.com");
		DatabaseReference newValue = mock(DatabaseReference.class);

		when(this.usersReference.child("aperdomobo")).thenReturn(newValue);
		userRepository.add(user);
		
		verify(newValue).setValue(user);
	}
	
	@Test
	public void exists() {
		User firstUser = new User();
		firstUser.setUsername("aperdomobo");
		firstUser.setEmail(("aperdomobo@gmail.com"));
		firstUser.setPassword("abcDEF123");

		User secondUser = new User();
		secondUser.setUsername("kenansamuel");
		secondUser.setPassword("1aB2cD3eF");
		secondUser.setEmail("kenan-samuel@gmail.com");

		DataSnapshot dataSnapshot = mock(DataSnapshot.class);
		List<DataSnapshot> childrens = new ArrayList<DataSnapshot>();

		DataSnapshot firstChildren = mock(DataSnapshot.class);
		when(firstChildren.getValue(User.class)).thenReturn(firstUser);

		DataSnapshot secondChildren = mock(DataSnapshot.class);
		when(secondChildren.getValue(User.class)).thenReturn(secondUser);

		childrens.add(firstChildren);
		childrens.add(secondChildren);

		when(dataSnapshot.getChildren()).thenReturn(childrens);

		userRepository.onDataChange(dataSnapshot);
		boolean exists = userRepository.exists("aperdomobo");

		assertTrue(exists);
	}
}
