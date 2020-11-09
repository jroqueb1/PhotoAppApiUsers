package com.appsdeveloperblog.photoapp.api.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appsdeveloperblog.photoapp.api.users.service.UserService;

@EnableGlobalMethodSecurity(prePostEnabled = true) //Enables Spring Security global method security 
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserService usersService;
	
	@Autowired
	private BCryptPasswordEncoder passEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();	//Disabling Cross Site Request Forgery
		http.headers().frameOptions().disable();	//Disabling frame options for H2 console
		
		http.authorizeRequests()
//			.antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"))	//Filter IP address
			.antMatchers(HttpMethod.POST, "/users").hasIpAddress(env.getProperty("gateway.ip"))
			.antMatchers("/h2-console/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilter(getAuthenticationFilter())
			.addFilter(new AuthorizationFilter(authenticationManager(), env));		
	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authFilter = new AuthenticationFilter(usersService, env, authenticationManager());
//		authFilter.setAuthenticationManager(authenticationManager());
		authFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
		return authFilter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {	
		auth.userDetailsService(usersService).passwordEncoder(passEncoder);
	}

}
