package com.irdaislakhuafa.alterraacademyfinalproject.swagger;

import com.irdaislakhuafa.alterraacademyfinalproject.AlterraAcademyFinalProjectApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.*;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerDocsConfiguration {
        @Bean
        public Docket docs() {
                return new Docket(DocumentationType.SWAGGER_2)
                                .select()
                                .apis(RequestHandlerSelectors
                                                .basePackage(AlterraAcademyFinalProjectApplication.class
                                                                .getPackageName() + ".controllers"))
                                .paths(PathSelectors.regex("/api/v1.*"))
                                .build()
                                .apiInfo(new ApiInfoBuilder()
                                                .title("School Library API Docs")
                                                .description("This is simple documentation to show how to consume School Library API")
                                                .version("0.1")
                                                .license("apache license")
                                                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                                                .contact(new Contact(
                                                                "Irda islakhu Afa",
                                                                "https://github.com/irdaislakhuafa",
                                                                "irdhaislakhuafa@gmail.com"))
                                                .build());
        }
}
