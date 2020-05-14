package br.com.fapen.estoque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.fapen.estoque.services.UsuarioService;

@EnableWebSecurity
public class EstoqueSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioService servicoDeUsuarios;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(servicoDeUsuarios).passwordEncoder(new BCryptPasswordEncoder());	
		
		//Cria o usuario Admin, se não existir
		if (!servicoDeUsuarios.userExists("admin")) {
			servicoDeUsuarios.addAdminUser();
		}
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/home").permitAll()
			.antMatchers("/api/**").permitAll()
			.antMatchers("/recuperarSenha/**").permitAll()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/img/**").permitAll()			
			.antMatchers(HttpMethod.POST, "/**/delete").hasAnyRole("ADMIN", "GERENTE")
			.antMatchers("/usuarios/**").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").defaultSuccessUrl("/home").permitAll()
			.and()
			.csrf().disable()
			.logout(). logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/home");
	}
}




