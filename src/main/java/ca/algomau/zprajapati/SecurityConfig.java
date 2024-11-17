package ca.algomau.zprajapati;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize ->
                authorize
                    // Allow access to H2 Console without authentication
                    .requestMatchers("/h2-console/**").permitAll()  // Allow H2 Console without login
                    .requestMatchers("/admin/**").authenticated()  // Protect admin pages, requiring login
                    .requestMatchers("/CSS/**", "/images/**").permitAll()  // Allow static resources (CSS, images)
                    .anyRequest().permitAll()  // Allow other pages without login
            )
            .formLogin(form ->
                form
                    .loginPage("/admin/login")  // Custom login page for admins
                    .defaultSuccessUrl("/admin/adminhome", true)  // Redirect after successful login
                    .failureUrl("/admin/login?error=true")  // Failure URL
                    .permitAll()
            )
            .logout(logout ->
                logout
                    .logoutUrl("/admin/logout")  // Custom logout URL
                    .logoutSuccessUrl("/admin/login?logout=true")  // Redirect after logout
                    .permitAll()
            );
            http
            .csrf().disable() // Disable CSRF for simplicity (should be enabled in production)
            .headers().frameOptions().sameOrigin();  // Allow frames to display the H2 Console
        return http.build();
    }
}
