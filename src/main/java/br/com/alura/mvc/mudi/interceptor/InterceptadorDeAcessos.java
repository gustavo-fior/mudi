package br.com.alura.mvc.mudi.interceptor;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.AsyncHandlerInterceptor;

import br.com.alura.mvc.mudi.model.Acesso;
import br.com.alura.mvc.mudi.repository.AcessoRepository;

public class InterceptadorDeAcessos implements AsyncHandlerInterceptor{

	// Não sei por qual motivo a anotação @Autowired não funciona, mesmo anotando esta classe com @Component ou outras Stereotypes
	private AcessoRepository acessoRepository;
	
	public InterceptadorDeAcessos(AcessoRepository acessoRepository) {
		this.acessoRepository = acessoRepository;
	} 
	
	// Metodo para antes de comecar o processamento
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Acesso acesso = new Acesso();

		// Endereco que o usuario tenta acessar
		acesso.setPath(request.getRequestURI());
		acesso.setData(LocalDateTime.now()); 
		
		// Passando o acesso para a requisicao para podermos pegar ele de novo no metodo afterCompletion
		request.setAttribute("acesso", acesso);
		
		return true;
				
	}
	
	// Metodo para depois de a resposta ser completa
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		// Recuperando o acesso da requisicao
		Acesso acesso = (Acesso) request.getAttribute("acesso");
		
		// Setando a duracao para a diferenca entre o setado na requisicao e o momento atual
		acesso.setDuracao(Duration.between(acesso.getData(), LocalDateTime.now()));
		
		acessoRepository.save(acesso);
		
	}
	
	
	
}
