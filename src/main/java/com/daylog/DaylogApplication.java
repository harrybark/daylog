package com.daylog;

import com.daylog.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class DaylogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaylogApplication.class, args);
    }

}
