package configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.saboresdigitais.quickeats.store.SaboresDigitaisApplication;
import com.saboresdigitais.quickeats.store.domain.repository.CustomerRepository;
import com.saboresdigitais.quickeats.store.domain.service.CustomerService;

@SpringBootTest(classes = SaboresDigitaisApplication.class)
public class BeanAvailabilityTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;
    
    @Test
    public void beansShouldBeAvailable() {
        assert passwordEncoder != null : "PasswordEncoder should be available";
        assert customerRepository != null : "CustomerRepository should be available";
        assert customerService != null : "CustomerService should be available";
    }
}
