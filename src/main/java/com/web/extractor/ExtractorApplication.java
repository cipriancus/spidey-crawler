package com.web.extractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.web.extractor.*")
public class ExtractorApplication {
  public static void main(String[] args) {
    SpringApplication.run(ExtractorApplication.class, args);
  }
}
