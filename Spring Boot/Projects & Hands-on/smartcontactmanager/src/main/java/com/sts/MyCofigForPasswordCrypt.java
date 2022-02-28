package com.sts;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyCofigForPasswordCrypt extends WebSecurityConfigurerAdapter {
	@Bean //this bean can be used anywhere in this project by using autowired
	public UserDetailsService get() {
		return new UserDetailsServiceImplSpringSecurity();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider provider() { //neccessary for getting data from database for authentication
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.get());
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(this.provider()); //specifiying what type of authentication, dao in this case
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/user/**").hasRole("USER").antMatchers("/**").permitAll().and().formLogin().loginPage("/login").and().csrf().disable(); // to give a custom page for login, one can give "...formLogin().loginPage("/signin").and()...." // here signin is the name of controller handler and from there we can accordingly create the pages, remember to keep the name of input boxes same as given in default login page provided by spring
		//above is a configuration which tells spring security to apply scope urls to which role, for instance any site with admin can only be accessed by user whose role field is admin in DB, this type of scoping has been made applicable for all form type logins and csrf mode of authentication is disabled
		//ensure to use standard param for hasRole such as USER for ROLE_USER given in DB for role
		
	}
}
