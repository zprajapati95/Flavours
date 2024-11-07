package ca.algomau.zprajapati;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "ca.algomau.zprajapati") // Ensure JPA entities are scanned
@EnableJpaRepositories(basePackages = "ca.algomau.zprajapati") // Ensure JPA repositories are scanned
public class Project1Application {

    public static void main(String[] args) {
        SpringApplication.run(Project1Application.class, args);
    }
}
