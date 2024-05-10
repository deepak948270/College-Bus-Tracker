package com.deepak.config;

import com.deepak.repository.SecurityUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final SecurityUserRepository securityUserRepository;

    public SecurityConfig(SecurityUserRepository securityUserRepository) {
        this.securityUserRepository = securityUserRepository;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService() {
        /*UserDetails adminUser = User.builder()
                .username("deepak")
                .password(passwordEncoder().encode("deepak"))
                .roles("ADMIN")
                .build();


        UserDetails normalUser = User.builder()
                .username("aman")
                .password(passwordEncoder().encode("aman"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(adminUser, normalUser);*/

        return username -> securityUserRepository
                .findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("invalid credentials"));
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService());

        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)

                .cors(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/public/**", "/home", "/","/newLogin").permitAll()
                        .requestMatchers("/css/**","/contact","/processcontact").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/signup/**").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(login -> login
                .loginPage("/newLogin")
                        .defaultSuccessUrl("/")
                        .loginProcessingUrl("/processlogin")
                        .permitAll())
                .exceptionHandling(exception->exception
                .accessDeniedPage("/accessDenied"))     

                .logout(Customizer.withDefaults())


                .build();
    }

}
