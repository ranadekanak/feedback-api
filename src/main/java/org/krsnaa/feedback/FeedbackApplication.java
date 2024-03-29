package org.krsnaa.feedback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FeedbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeedbackApplication.class, args);
    }
}
