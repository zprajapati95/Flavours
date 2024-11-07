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
                    .requestMatchers("/admin/**").authenticated() // Protect admin pages
                    .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Adjust paths for static resources
                    .anyRequest().permitAll() // Allow access to other pages
            )
            
            .formLogin(form -> 
                form
                    .loginPage("/admin/login") // Custom admin login page
                    .defaultSuccessUrl("/admin/adminhome", true) // Redirect to admin home after successful login
                    .failureUrl("/admin/login?error=true") // Redirect to login page with error on failure
                    .permitAll() // Allow everyone to see the login page
            )
            .logout(logout -> 
                logout
                    .logoutUrl("/admin/logout") // Custom logout URL
                    .logoutSuccessUrl("/admin/login?logout=true") // Redirect to login page after logout
                    .permitAll() // Allow everyone to log out
            )
            .csrf().disable(); // Disable CSRF for simplicity (not recommended in production)

        return http.build();
    }
}
