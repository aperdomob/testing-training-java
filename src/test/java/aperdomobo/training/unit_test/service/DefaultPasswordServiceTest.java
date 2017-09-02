package aperdomobo.training.unit_test.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class DefaultPasswordServiceTest {
	private PasswordService passwordService = new DefaultPasswordService();
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{"abcdEFGH", false},
			{"EFGH1234", false},
			{"1234abcd", false},
			{"abCD12", false},
			{"a1CDb23Ec", true},
			{"abC.D1,2", false},
		});
	}
	
	@Parameter
	public String password;
	
	@Parameter(1)
	public boolean expected;

	@Test
	public void isValidPasswordTest() {
		boolean isValidPassword = this.passwordService.isValidPassword(password);
		assertEquals(expected, isValidPassword);
	}
}
