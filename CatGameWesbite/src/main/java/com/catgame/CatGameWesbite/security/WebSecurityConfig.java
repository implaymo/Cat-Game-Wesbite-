package com.catgame.CatGameWesbite.security;


import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/css/**", "/javascript/**", "/images/**").permitAll()
                .requestMatchers("/login", "/registration", "/forgot_password", "/reset_password", "/successregistration").permitAll()
                .anyRequest().authenticated() 
            )
            .formLogin((form) -> form
                .loginPage("/login") 
                .permitAll()
                .successHandler(customAuthenticationSuccessHandler())
                .failureUrl("/login?error=true")
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public CustomLogoutSuccessHandler customLogoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean 
    public PasswordEncoder passwordEncoder() { 
       return new BCryptPasswordEncoder(); 
    } 

    @Bean CustomPasswordValidation customPasswordValidation() {
        return new CustomPasswordValidation();
    }

}