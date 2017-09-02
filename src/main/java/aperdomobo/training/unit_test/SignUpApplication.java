package aperdomobo.training.unit_test;

import java.io.InputStream;
import java.util.Arrays;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SignUpApplication extends SpringBootServletInitializer
{
  private static final Logger logger = LoggerFactory.getLogger(SignUpApplication.class);
  
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(SignUpApplication.class);
  }

  public static void main(String[] args) throws Exception {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    InputStream serviceAccount = classloader.getResourceAsStream("certificate.json");

    FirebaseOptions options = new FirebaseOptions.Builder()
      .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
      .setDatabaseUrl("https://training-test-1671c.firebaseio.com")
      .build();

    FirebaseApp.initializeApp(options);

    ApplicationContext context = SpringApplication.run(SignUpApplication.class, args);
    
    logger.info("Let's inspect the beans provided by Spring Boot:");
    
    String[] beanNames = context.getBeanDefinitionNames();
    Arrays.sort(beanNames);
    
    for (String beanName : beanNames) {
      logger.info(beanName);
    }
  }
}
