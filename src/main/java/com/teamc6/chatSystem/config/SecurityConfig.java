package com.teamc6.chatSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        System.out.println("encode");
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET,"api/v1/users").permitAll()
                        .requestMatchers(HttpMethod.POST,"api/v1/users").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/v1/users/search").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"api/v1/users/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"api/v1/users/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"api/v1/groups/{id}").hasAuthority("USER")

                        .requestMatchers(HttpMethod.GET,"api/v1/users/{id}/groups").hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST,"api/v1/users/{id1}/friends/{id2}").hasAuthority("USER")
                        .requestMatchers(HttpMethod.PUT,"api/v1/groups/{id}/members/{member_id}").hasAuthority("USER")
                        .requestMatchers(HttpMethod.PUT,"api/v1/groups/{id}/admins/{admin_id}").hasAuthority("USER")
                        .requestMatchers(HttpMethod.GET,"api/v1/groups/{id}/messages").hasAuthority("USER")

                        .anyRequest().authenticated()
                )

                .httpBasic(withDefaults()).csrf(AbstractHttpConfigurer::disable).build();

    }
}
