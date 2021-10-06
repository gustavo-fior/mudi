package br.com.alura.mvc.mudi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.alura.mvc.mudi.interceptor.InterceptadorDeAcessos;
import br.com.alura.mvc.mudi.repository.AcessoRepository;

// Classe para fazermos a configuracao de interceptors
@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
	private AcessoRepository acessoRepository;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		//Adicionando nosso interceptador e setando as URLs que ele vai trabalhar para todas
		registry.addInterceptor(new InterceptadorDeAcessos(acessoRepository)).addPathPatterns("/**");
	}
	
}
