package com.ge.digitaltwin.tutorial.postprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class PostProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostProcessingApplication.class, args);
    }
}
