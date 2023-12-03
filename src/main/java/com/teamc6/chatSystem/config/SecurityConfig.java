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
                        .requestMatchers(HttpMethod.DELETE,"api/v1/users/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"api/v1/users/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"api/v1/users/{id}/groups").hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST,"api/v1/users/{id1}/friends/{id2}").hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST,"api/v1/users").permitAll()

//                        .requestMatchers(HttpMethod.GET,"api/v1/users/search/username={username}").permitAll()
//                        .requestMatchers(HttpMethod.GET,"api/v1/users/filter/username={username}").permitAll()
//                        .requestMatchers(HttpMethod.GET,"api/v1/users/search/id={id}").permitAll()
//                        .requestMatchers(HttpMethod.GET,"api/v1/users:{id}/friends").x`xpermitAll()
//                        .requestMatchers(HttpMethod.GET,"api/v1/users:{id}/user-active-sessions").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/v1/groups/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"api/v1/groups/{id}/members").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"api/v1/groups/{id}/admins").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"api/v1/groups/{group_id}/members/{member_id}").hasAuthority("USER")

                        .requestMatchers(HttpMethod.PUT,"api/v1/groups/{group_id}/admins/{admin_id}").hasAuthority("USER")

                        .anyRequest().authenticated()
                )

                .httpBasic(withDefaults()).csrf(AbstractHttpConfigurer::disable).build();

    }
}
