package com.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication().withUser("test").password("pwd123").roles("USER");
	}
	
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests().anyRequest().authenticated().and().formLogin()
		.loginPage("/login").defaultSuccessUrl("/titulos", true)
		.permitAll().and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		http.csrf().disable();
	}

	/* não bloquear algumas URL's */
	// ** recursivo tudo do layout para frente está liberado
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
		web.ignoring().antMatchers("/js/**");
		web.ignoring().antMatchers("/css/**");
		web.ignoring().antMatchers("/fonts/**");
		web.ignoring().antMatchers("/images/**");
		web.ignoring().antMatchers("/layout/**");
	}

}
