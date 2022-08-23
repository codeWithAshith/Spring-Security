package com.codeWithAshith.SpringSecurity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
// We are adding method security annotations to enable processing based on different roles.
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    // With the WebSecurityConfigureAdapter,
    // we use an AuthenticationManagerBuilder to set our authentication context.

    //Now, if we want to avoid deprecation,
    // we can define a UserDetailsManager or UserDetailsService component

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(bCryptPasswordEncoder.encode("user"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode("admin"))
                .roles("USER", "ADMIN")
                .build());
        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/h2/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/user/**")
                .hasAnyRole("USER", "ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .csrf()
                .disable();

        return http.build();
    }

}
