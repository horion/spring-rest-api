package br.com.alura.forum.config.doc;

import br.com.alura.forum.model.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket forumApi(){
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
                .paths(PathSelectors.ant("/**")).build().ignoredParameterTypes(Usuario.class)
                .globalOperationParameters(Arrays.asList(new ParameterBuilder()
                        .name("Authorization")
                        .description("Header para token JWT")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build()
                ));
    }



}
