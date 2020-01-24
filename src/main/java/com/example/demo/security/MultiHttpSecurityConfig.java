package com.example.demo.security;


import java.util.logging.Logger;

import javax.sql.DataSource;

import org.aspectj.weaver.tools.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
@Order(SecurityProperties.BASIC_AUTH_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled=true)

/*
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER)
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(securedEnabled = true)
*/

public class MultiHttpSecurityConfig {
	
	static Logger log = Logger.getLogger("SecurityConfig.java");
	
	@Autowired
	DataSource dataSource;
	
    @Value("${ldap.urls}")
	private String ldapUrls;

	@Value("${ldap.base.dn}")
	private String ldapBaseDn;

	@Value("${ldap.username}")
	private String ldapSecurityPrincipal;

	@Value("${ldap.password}")
	private String ldapPrincipalPassword;

	@Value("${ldap.user.dn.pattern}")
	private String ldapUserDnPattern;

	@Value("${auth.enabled}")
	private String authEnabled;
	
	@Bean
	public static CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
		return new CustomBasicAuthenticationEntryPoint();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		switch(authEnabled) {
		
			case "DATABASE":
				log.info("SPRING SECURTIY INITILISED WITH DATABASE");
				auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT USERNAME,PASSWORD,ENABLED FROM bootapp.USERS WHERE USERNAME=?")
				.authoritiesByUsernameQuery("SELECT USERNAME,ROLE FROM bootapp.USER_ROLES WHERE USERNAME=?");
				break;
				
			case "LDAP":
				log.info("SPRING SECURTIY INITILISED WITH LDAP");
				auth
				.ldapAuthentication().contextSource()
				.url(ldapUrls + ldapBaseDn)//.managerDn(ldapSecurityPrincipal).managerPassword(ldapPrincipalPassword)
				.and()
				.userDnPatterns(ldapUserDnPattern).groupSearchBase("ou=Roles");
				break;
				
			case "SPECIFIC":
				log.info("SPRING SECURTIY INITILISED WITH HARDCODED USERNAME AND PASSWORD");
				auth.inMemoryAuthentication().withUser("praveen").password("123").roles("USER");
				auth.inMemoryAuthentication().withUser("kumar").password("123").roles("USER");
				auth.inMemoryAuthentication().withUser("appuser").password("123").roles("USER","ADMIN");
				break;
				
			default:
				break;
		}
			
	}
	
	@Configuration
	@Order(1)
	public static class APIConfiguration extends WebSecurityConfigurerAdapter {
		private static String REALM="MY_TEST_REALM";
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			log.info("SPRING MULTI HTTP SECURITY : APICONFIGURATION");
			/*
			 * http.authorizeRequests() .antMatchers("/webapi/**").hasRole("ADMIN")
			 * .and().httpBasic().realmName(REALM).authenticationEntryPoint(
			 * getBasicAuthEntryPoint())
			 * .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.
			 * STATELESS);
			 */
			
			http.csrf().disable().antMatcher("/webapi/**").authorizeRequests().anyRequest().hasRole("ADMIN").and().httpBasic();
		}
	}
	
	@Configuration
	@Order(2)
	public static class LoginPageConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			log.info("SPRING SECURTIY LoginPageConfiguration");
			http.csrf()
			.requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/login"))
			.and().authorizeRequests().antMatchers("/home").hasRole("USER")
									  .antMatchers("/app/paygroup").hasRole("ADMIN")
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