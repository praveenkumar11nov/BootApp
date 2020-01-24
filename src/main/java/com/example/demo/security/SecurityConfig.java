package com.example.demo.security;

import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@SuppressWarnings("deprecation")
//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	Logger log = Logger.getLogger("SecurityConfig.java");
	
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
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("SPRING SECURTIY CONFIGURATION");
		http.csrf()
		.requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/login"))
		.and().authorizeRequests().antMatchers("/home").hasRole("USERS")
								  .antMatchers("/app/paygroup").hasRole("ADMIN")
		.and().formLogin().defaultSuccessUrl("/home").loginPage("/login")
		.and().logout().permitAll();
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
	
	@Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/*.css");
		web.ignoring().antMatchers("/*.js");
	}
	
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
	
}
