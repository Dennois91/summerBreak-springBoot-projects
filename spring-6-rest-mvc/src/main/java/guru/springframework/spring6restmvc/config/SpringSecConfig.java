package guru.springframework.spring6restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/swagger-ui/**","/v3/api-docs/**","/swagger-ui.html","/v3/api-docs.yaml")
                .permitAll()
                .anyRequest().authenticated()
                .and().oauth2ResourceServer().jwt();

        return http.build();
    }
}
