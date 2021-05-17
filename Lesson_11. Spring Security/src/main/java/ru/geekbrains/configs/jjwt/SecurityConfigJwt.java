package ru.geekbrains.configs.jjwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfigJwt extends WebSecurityConfigurerAdapter {

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("----- Security Configuration with Java JSON Web Token -----");
        http
                    .csrf().disable()
                    .cors().disable()
                    .authorizeRequests()
                    // страница со списком товаров, на которой можно добавлять позиции и редактировать существующие (админы и менеджеры)
                    .antMatchers("/api/v1/products/edit/**").hasAnyRole("ADMIN", "MANAGER")
                    // страница со списком пользователей (админ - просмотр, суперадмин - создавать и редактировать)
                    .antMatchers("/api/v1/users").hasAnyRole("ADMIN", "SUPERADMIN")
                    .antMatchers("/api/v1/users/**").hasAnyRole("SUPERADMIN")
                    .anyRequest().permitAll()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .headers().frameOptions().disable();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}