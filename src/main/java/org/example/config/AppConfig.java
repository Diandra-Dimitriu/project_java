package org.example.config;

import org.example.database.UserDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UserDAO userDAO() {
        return new UserDAO();
    }
}