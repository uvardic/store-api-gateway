package store.server.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer cors() {
        return new WebMvcConfigurerImpl();
    }

    private static class WebMvcConfigurerImpl implements WebMvcConfigurer {

        private static final String[] ALLOWED_ORIGINS = new String[] {
                "localhost:3000",
        };

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/graphql").allowedOrigins(ALLOWED_ORIGINS);
        }

    }

}
