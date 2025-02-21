package org.maping.maping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.maping.maping")
@EnableJpaRepositories(basePackages = "org.maping.maping.repository")
public class MapingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapingApplication.class, args);
    }

}
