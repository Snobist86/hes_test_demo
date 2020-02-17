package by.pankov.hes_test_demo.configuration;

import by.pankov.hes_test_demo.service.impl.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAccountService accountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                     .antMatchers("/user/{id}/edit", "/user/new").hasAnyAuthority("ROLE_ADMIN")
                     .antMatchers("/user", "/user/{id}").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                     .antMatchers("/login").permitAll()
                     .anyRequest().authenticated()
//                       .anyRequest().permitAll()
                .and()
                     .formLogin()
                     .loginPage("/login")
                .and()
                     .logout()
                     .logoutUrl("/logout")
                     .logoutSuccessUrl("/login")
                .and()
                     .exceptionHandling()
                     .accessDeniedPage("/403");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(accountService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
}
