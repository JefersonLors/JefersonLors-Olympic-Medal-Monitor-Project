package com.authentication_ms.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity ) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable()) //desativa as configurações padrão
                .sessionManagement(session -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS)) //muda de statefull para stateless a forma de autenticação
                .authorizeHttpRequests( authorize -> authorize
                        .requestMatchers( HttpMethod.POST, "/auth/signIn").permitAll()
                        .requestMatchers( HttpMethod.POST, "/auth/signUp").permitAll()
                        .requestMatchers( HttpMethod.DELETE, "/user/{id}").hasRole("ADMIN")
                        .requestMatchers( HttpMethod.PUT, "/user/{id}").hasRole("ADMIN")
                        .requestMatchers( HttpMethod.GET, "/user").hasRole("ADMIN")
                        .requestMatchers( HttpMethod.GET, "/user/id").hasRole("ADMIN")
                        .requestMatchers( HttpMethod.GET, "/role").hasRole("ADMIN")
                        .requestMatchers( HttpMethod.GET, "/role/id").hasRole("ADMIN")
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("8086/actuator/**", "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
