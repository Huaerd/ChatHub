package net.huaerd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("net.huaerd.model.entity")
@EnableJpaRepositories("net.huaerd.repository")
public class ChatHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatHubApplication.class,args);
    }
}
