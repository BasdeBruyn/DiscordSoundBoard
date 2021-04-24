package nl.basdebruyn.soundboardapi.auth.configurers;

import nl.basdebruyn.soundboardapi.auth.properties.CorsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsSecurityConfigurer implements WebMvcConfigurer {
    private final CorsProperties corsProperties;

    public CorsSecurityConfigurer(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(CorsProperties corsProperties) {
        return new CorsSecurityConfigurer(corsProperties);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedMethods(corsProperties.getAllowedMethods())
                .allowedOrigins(corsProperties.getAllowedOrigins());
    }
}
