package com.ge.digitaltwin.tutorial.result;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EntityScan(basePackages = "com.ge.digitaltwin.tutorial.common")
public class ResultPersistenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResultPersistenceApplication.class, args);
    }

}
