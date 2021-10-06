package br.com.alura.mvc.mudi;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Classe de configuração do Spring Security
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// DataSource injetado (nosso banco de dados)
	@Autowired
	private DataSource dataSource;
	
	// Método padrão de configuração
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Aqui simplificamos o código padrão de https://spring.io/guides/gs/securing-web/
		http
			.authorizeRequests()
			.antMatchers("/home/**").permitAll() // Requests que não precisarão estar logadas (/home e /home/qualquercoisa)
			.anyRequest().authenticated() // Requests que precisarão estar logadas (as outras que não as de cima)
		.and()
			.formLogin(form -> form
					.loginPage("/login") // URL da página de login
					.defaultSuccessUrl("/usuario/pedido", true) // URL para redirecionar quando o login for feito com sucesso
					.permitAll() // Não precisa estar autenticado para acessar o /login
					)
			.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/home")) // Após logout user volta para página home
			// Desabilitando essa config para não ocorrer erro ao cadastrar o pedido
			.csrf().disable();
		
	}
	
	// Método para trabalhar com JDBC Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// BCrypt para criptografar as senhas já vem no pacote de Spring Security
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(encoder);
		
	}
	
	
}
