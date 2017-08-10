package aperdomobo.training.unit_test.service;

public interface EmailService {
	boolean isValidEmailFormat(String email);
	boolean isValidEmail(String email);
	boolean isUsedEmail(String email);
}
