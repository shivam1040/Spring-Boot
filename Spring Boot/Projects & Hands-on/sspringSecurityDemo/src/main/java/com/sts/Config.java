package com.sts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //this tag is neccesary for using annotation for associating roles/permission with handlers, when this is used one doesn't need to define hasRole/hasAuthorities with antMatchers
public class Config extends WebSecurityConfigurerAdapter {
	@Autowired
	private PasswordEncoder passwordEncoder; //this bean gets instantiatized because of Bean tag in PasswordConfig class, this class's method is used below in userdetail method for in memory authentication
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/api").hasRole(Role.STUDENT.name()).antMatchers(HttpMethod.GET, "/").hasAuthority(Permission.READ.name()).anyRequest().authenticated().and().httpBasic(); //this configuration is for restricitiong access to a particular role by means of enum
		//in above, hasauthority limits the associated matcher to roles which have read permission
		//disabling csrf lets one to do different operations(POST, GET etc.) from same login which is not encouraged
		
		//http.authorizeRequests().anyRequest().authenticated().and().httpBasic(); //this configuration is for basicAuth type authentication
		
		//use defaultSuccessUrl("/", true), use this to take form based login to default page after success
		
		//use rememberMe() for defining remember me login for a form login, use tokenValidity() and key(String) along with this for defining parameters for remember me cookie generated and key for encryption of username and expiration time, further define a remember me checkbox in login page with remember-me id, if you dont want to set specific id then use remembermeParameter(custom id from view) alongwith rememember me
		
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().authorizeRequests().antMatchers("/api").hasRole(Role.STUDENT.name()).antMatchers(HttpMethod.GET, "/").hasAuthority(Permission.READ.name()).anyRequest().authenticated().and().httpBasic(); //this configuration is for restricitiong access to a particular role by means of enum
		//in avobe csrftokenrepo is used to generate csrf based on custom settings
		
		//.logout().logoutUrl().logoutRequestMatcher(new AntPathRequestMatcher(url, httpmethod)).clearAuthentication().invalidateHttpSession(true).deleteCookies(cookie1 name, cookie2 name...).logoutSuccessURl(url) //use this for custom logout
		
		//passwordParameter().usernameParameter() can be used along with formLOgin to define custom view parameters for username password
		
		//formlogin().loginpage(String).permitall.defaultSuccessUrl(parmas).passwordParameter(id from view).usernameParameter(id from view).and().rememberMe().tokenValiditySeconds().key(string).remembermeparameter(Id from view).and().logouturl(url).logoutRequestMatcher(new AntPathRequestMAtcher(parmaurl, param http method)).clearAuthentication(true).invalidatesession(true).deletecookies(cookiename1,...).logoutSuccessurl(url)
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() { //this is one way to predefine username and roles to use for authentication, encrypted password is necessary in this method
		//UserDetails user1=User.builder().username("admin").password(passwordEncoder.encode("admin")).roles("STUDENT").build();
		UserDetails user1=User.builder().username("admin").password(passwordEncoder.encode("admin")).roles(Role.STUDENT.name()).authorities(Role.STUDENT.getGrantedAuthorities()).build(); //using enums for defining roles
		//in above, authorities is used to give relevant permissions to it

		UserDetails user2=User.builder().username("admin1").password(passwordEncoder.encode("admin")).roles(Role.ADMIN.name()).authorities(Role.ADMIN.getGrantedAuthorities()).build(); //user with admin role
		return new InMemoryUserDetailsManager(user1, user2); //stores username password in memory for authentication
	}
	
	
	
}
