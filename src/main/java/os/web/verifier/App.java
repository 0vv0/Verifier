package os.web.verifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.logging.Logger;

@SpringBootApplication
@EnableWebMvc
public class App {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
