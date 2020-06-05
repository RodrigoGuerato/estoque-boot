package br.com.fapen.estoque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.fapen.estoque.filters.JwtAuthenticationEntryPoint;
import br.com.fapen.estoque.filters.JwtRequestFilter;
import br.com.fapen.estoque.services.UsuarioService;

@Configuration
@EnableWebSecurity
public class EstoqueSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioService servicoDeUsuarios;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(servicoDeUsuarios).passwordEncoder(passwordEncoder());

		// Cria o usuario Admin, se não existir
		if (!servicoDeUsuarios.userExists("admin")) {
			servicoDeUsuarios.addAdminUser();
		}
	}

	@Order(1)
	@Configuration
	public static class ApiConfig extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
		
		@Autowired
		private JwtRequestFilter jwtRequestFilter;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.antMatcher("/api/**").authorizeRequests()
				.antMatchers("/api/usuarios/login").permitAll()
				.antMatchers("/api/produtos/**").permitAll()
				.anyRequest().authenticated()
				.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().csrf().disable();
			
			http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		}

	}

	@Order(2)
	@Configuration
	public static class WebConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/home").permitAll()
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

}
