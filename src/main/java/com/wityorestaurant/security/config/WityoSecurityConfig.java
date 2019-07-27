package com.wityorestaurant.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WityoSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JwtAuthenticationEntryPoint customAuthEntryPoint;

	@Autowired
	private UserDetailsService RestaurantUserDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(RestaurantUserDetailsService).passwordEncoder(passwordEncoder);
	}

	
	/* Second step after setting up the AuthenticationManagerBuilder (above code)
	 * This is to register our AuthenticationManager as a bean so that we can access it,
	 * in our controllers 
	 */
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationFilter();
	}


	/* @Description:-
	 * 1. It is the authenticationEntryPoint which handles what exception needs to be thrown when somebody is not authorized
	 * 2. We are letting the spring security know that this session is going to be stateless as we will handle the state in client side.
	 * 3. Ant Matchers are used to make some of the URLs accessible to public with permitall().anyRequest().authenticated(); method. 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(customAuthEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().headers()
			.frameOptions().sameOrigin()
			.and().authorizeRequests()
			.antMatchers("/api/restaurant/**").permitAll()
			.anyRequest().authenticated();
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public JwtAuthenticationFilter authenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	

}
