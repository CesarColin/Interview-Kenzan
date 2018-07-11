package com.kenzan.interview.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author Colin, Cesar
 *
 * Extends WebSecurityConfigurerAdapter to configure requested security
 * Specifically requiring  authentication header for DELETE http method to deactivate an employee
 */
@Configuration
@EnableWebSecurity
public class EmployeeConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.DELETE, "/employees/{id}").authenticated()
            .anyRequest().permitAll()
//            .anyRequest().authenticated()
            .and()
            .httpBasic();
    }

	/**
	 * Using a deprecated method to set hard coded user and password because is just a demo project.
	 * For PROD this should be set as properties outside the code.
	 */
	@Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
             User.withDefaultPasswordEncoder()
                .username("carlin")
                .password("k3n24n")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
	}
}
