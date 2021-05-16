package ru.geekbrains.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

//@EnableWebSecurity
@Slf4j
public class SecurityConfigJdbc extends WebSecurityConfigurerAdapter {

    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
        log.info("----- Create JdbcUserDetailsManager bean -----");
        return new JdbcUserDetailsManager(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("----- Security Configuration JDBC -----");
        http.authorizeRequests()
                .antMatchers("/auth/**").authenticated()
                .antMatchers("/user").authenticated()
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin();
    }

}
