package com.github.tmd.gamelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GameLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameLogApplication.class, args);
    }

}
