package de.chainsaw.app.bank.config;

import static java.util.stream.Collectors.toSet;

import java.util.stream.Stream;
import javax.servlet.ServletContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

  @Bean
  public Docket api(ServletContext servletContext) {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("de.chainsaw.app.bank.controller"))
        .build()
        .host("bank.chainsaw.de")
        .protocols(Stream.of("http", "https").collect(toSet()));
  }
}
