package com.teamc6.chatSystem.config;

import com.teamc6.chatSystem.api.GroupAPI;
import com.teamc6.chatSystem.api.UserAPI;
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
                        .requestMatchers(HttpMethod.GET, UserAPI.ALL).permitAll()
                        .requestMatchers(HttpMethod.DELETE, UserAPI.DELETE).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, UserAPI.GROUPS).hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST, UserAPI.ADD_FRIENDS).hasAuthority("USER")

                        .requestMatchers(HttpMethod.GET, GroupAPI.GET).hasAuthority("USER")
                        .requestMatchers(HttpMethod.PUT,GroupAPI.ADD_MEMBER).hasAuthority("USER")

                        .anyRequest().authenticated()
                )

                .httpBasic(withDefaults()).csrf(AbstractHttpConfigurer::disable).build();

    }
}
