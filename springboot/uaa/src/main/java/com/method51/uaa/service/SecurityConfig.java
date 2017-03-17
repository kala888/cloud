package com.method51.uaa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MongoUserDetailsService userDetailsService;



    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                // Spring Security should completely ignore URLs starting with
                // /resources/
                .antMatchers("/mgmt", "/mgmt/**", "/swagger-ui.html")
                .antMatchers("/configuration/**", "/swagger**", "/webjars/**", "/v2/**");
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/configuration/**", "/swagger**", "/webjars/**", "/v2/**").permitAll();
        // @formatter:off
        http.authorizeRequests().antMatchers("/mgmt", "/mgmt/**", "/swagger-ui.html").permitAll()
                .antMatchers("/configuration/**", "/swagger**", "/webjars/**", "/v2/**").permitAll()// swagger
                .anyRequest().authenticated().and().csrf().disable();
        // @formatter:on
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }



    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws
    // Exception {
    // auth.inMemoryAuthentication().withUser("user").password("testpass").roles("USER");
    // }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
