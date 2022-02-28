package com.sts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { //changing default configuration of spring security such as username password, login page etc.
	
	@Autowired
	private UserDetailsServiceImpl customUserDetailsService;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { //configuring settings of spring security
		http.csrf().disable().cors().disable().authorizeRequests().antMatchers("/token").permitAll().anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling().authenticationEntryPoint(entryPoint);
		//method chaining//no security on /token and security on all other urls, csrf/cors is disabled, session management policy is set like REST, stateless, and error handling block is jwtauthenticationentrypoint class
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); //adding filter before every request
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { //used to configure which type of authentication we want
		auth.userDetailsService(customUserDetailsService); //getting user details, loadbyusername of userdetailsimpl interface is automatically called by spring to get the username and password
	} 
	
	@Bean
	public PasswordEncoder passwordEncoder() { //setting password encoding type
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean //declaring beans for use when we want autowiring
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
}