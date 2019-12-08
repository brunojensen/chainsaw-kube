package de.chainsaw.app.bank;

import de.chainsaw.app.testsupport.OAuthTestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = OAuthTestConfiguration.class)
@ActiveProfiles("test")
public class BankApplicationTests {

  @Test
  public void contextLoads() {
  }

}
