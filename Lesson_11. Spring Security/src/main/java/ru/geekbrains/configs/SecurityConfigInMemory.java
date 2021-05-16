package ru.geekbrains.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@EnableWebSecurity
@Slf4j
public class SecurityConfigInMemory extends WebSecurityConfigurerAdapter {

/**
*   https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/boot-features-security.html
*
*   The default UserDetailsService has a single user. The user name is 'user', and the password is random and is
*   printed at INFO level when the application starts, as shown in the following example:
*     Using generated security password: 78fa095d-3f4c-48b1-ad50-e24c31d5cf35
*/

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}111")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2y$12$GtCGGPO9Zw4Lz3J9XP5XW.xxf2f3fmhOyp9xIs4I71UQ/OjTGcUue")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("----- Security Configuration In Memory -----");
        http.authorizeRequests()
                .antMatchers("/auth/**").authenticated()
                .antMatchers("/user").authenticated()
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin();
    }
//                .loginPage()
//                .successForwardUrl()
//                .failureForwardUrl()
//                .passwordParameter()
//                .usernameParameter();
}
