/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.configuration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 *
 * @author tnowicki
 */
@Configuration
@EnableWebMvcSecurity
@ComponentScan
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()

                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }
protected static final Logger logger = Logger.getLogger(AbstractConfig.class);
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        logger.debug("CONFIGURING GLOBAL SECURITY!!!");
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
}