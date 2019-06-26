package com.tinyauction.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.tinyauction.service.UserService;




@Configuration
@EnableWebSecurity
public class TinyAuctionSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserService userService;
	
	@Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());

	}

	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		http.antMatcher("login").formLogin()
//			.loginPage("/showLoginPage")
//			.loginProcessingUrl("/authenticateTheUser")
//			.successHandler(customAuthenticationSuccessHandler)
//			.permitAll()
//		.and()
//		.logout().permitAll();
////		.and()
////		.exceptionHandling().accessDeniedPage("/access-denied");
		
		http.authorizeRequests()
		  .antMatchers("/auction/showNewAuctionForm").authenticated()
		  .antMatchers("/auction/processNewAuctionForm").authenticated()
		  .antMatchers("/auction/placeBid").authenticated()
		  .antMatchers("/account/information").authenticated()
	      .anyRequest().permitAll()
	      .and()
	    .formLogin()
	    .loginPage("/showLoginPage")
		.loginProcessingUrl("/authenticateTheUser")
		.successHandler(customAuthenticationSuccessHandler)
		.permitAll()
	      .and()
	      .logout().logoutSuccessUrl("/").permitAll()
	      .and()
	      .csrf().disable();
	}



	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
		
	}
	
}
