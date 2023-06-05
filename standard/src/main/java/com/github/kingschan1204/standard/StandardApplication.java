package com.github.kingschan1204.standard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kingschan
 */
@SpringBootApplication
public class StandardApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StandardApplication.class);
        app.run(args);
    }

}
