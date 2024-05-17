package cloud.ieum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class IeumApplication {

	public static void main(String[] args) {
		SpringApplication.run(IeumApplication.class, args);
	}

}
