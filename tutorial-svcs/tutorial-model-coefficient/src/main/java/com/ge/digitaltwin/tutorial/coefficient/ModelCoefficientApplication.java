package com.ge.digitaltwin.tutorial.coefficient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EntityScan(basePackages = "com.ge.digitaltwin.tutorial.common.coefficient")
public class ModelCoefficientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModelCoefficientApplication.class, args);
    }

}
