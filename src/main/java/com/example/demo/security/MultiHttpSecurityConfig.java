package com.example.demo.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)

/*
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER)
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(securedEnabled = true)
*/

public class MultiHttpSecurityConfig {
	
    @Autowired
    private DataSource datasource;

   /* @Autowired
    private CustomUserDetailsService userDetailsService;*/
	
	@Bean
	public static CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
		return new CustomBasicAuthenticationEntryPoint();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication().withUser("praveen").password("123").roles("USER");
		auth.inMemoryAuthentication().withUser("kumar").password("123").roles("USER");
		auth.inMemoryAuthentication().withUser("appuser").password("123").roles("ADMIN");
		
//		auth.jdbcAuthentication().dataSource(datasource)
//		.usersByUsernameQuery("select username,password, enabled from bootapp.USERS where username=?")
//		.authoritiesByUsernameQuery("select USERNAME,ROLE from bootapp.USER_ROLES where username=?");
		
	}
	
	@Configuration
	@Order(2)
	public static class APIConfiguration extends WebSecurityConfigurerAdapter {
		private static String REALM="MY_TEST_REALM";
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			System.out.println("Spring Multi Http Security : APIConfiguration _____________________________");
			//http.csrf().disable().authorizeRequests()
			http.authorizeRequests()
			.antMatchers("/webapi/**").hasRole("ADMIN")
			.and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}
	}
	
	@Configuration
	@Order(1)
	public static class LoginPageConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			System.out.println("Spring Multi Http Security : LoginPageConfiguration _____________________________");
			http.csrf()
			.requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/login"))
			.and().authorizeRequests().antMatchers("/home").hasRole("USER")
			.and().formLogin().defaultSuccessUrl("/home").loginPage("/login")
			.and().logout().permitAll();
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/*.css");
			web.ignoring().antMatchers("/*.js");
		}

		@SuppressWarnings("deprecation")
		@Bean
		public static NoOpPasswordEncoder passwordEncoder() {
			return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
		}
	}
}