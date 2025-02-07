package odin.stamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAspectJAutoProxy  // AOP 활성화
@EnableJpaAuditing
@EnableAsync
public class StampApplication {

	public static void main(String[] args) {
		SpringApplication.run(StampApplication.class, args);
	}

}
