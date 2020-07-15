package com.essejali.essejali.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	@Autowired
	private AutenticationService autenticationService;
	
	//Configuracoes de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticationService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	//Configuracoes de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/img/essejali-logo.png").permitAll()
		.antMatchers("/index.html").permitAll()
		.antMatchers("/fontawesome/**").permitAll()
		.antMatchers("/styles.css").permitAll()
		.antMatchers("/bootstrap.min.css").permitAll()
		.antMatchers("/bootstrap-grid.min.css").permitAll()
		.antMatchers(HttpMethod.GET, "/read-check").permitAll()
		.antMatchers(HttpMethod.GET, "/read-check/check").permitAll()
		.antMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
		.anyRequest().authenticated()
		.and()
        .formLogin()
        .loginProcessingUrl("/signin")
        .loginPage("/login").permitAll()
        .usernameParameter("txtUsername")
        .passwordParameter("txtPassword")
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
        .and()
        .rememberMe().tokenValiditySeconds(2592000).key("mySecret!").rememberMeParameter("checkRememberMe");
		
		http.csrf().disable();
        http.headers().frameOptions().disable();
	}
	
	
	//Configuracoes de recursos estaticos(js, css, imagens, etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
}
