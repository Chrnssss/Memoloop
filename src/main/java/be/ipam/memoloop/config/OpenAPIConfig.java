package be.ipam.memoloop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenAPIConfig {
    @Bean
    public OpenAPI memoloopApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("MemoLoop API")
                        .description("API REST pour gérer les decks et les flashcards")
                        .version("1.0.0"));
    }
}