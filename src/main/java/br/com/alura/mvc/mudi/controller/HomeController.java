package br.com.alura.mvc.mudi.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.StatusPedido;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

//Anotamos a classe para linkar ela com o Spring (funciona como uma servlet)
@Controller
@RequestMapping("home")
public class HomeController {

	// Serve para nos dar uma instancia do repository com injecao de dependencias
	// Outra opcao seria criar o construtor e passar uma instancia de
	// pedidoRepository
	// Para essa anotacao funcionar, a classe a ser instanciada deve ser anotada
	// para o Spring poder fornecer uma instancia dela
	// Nesse caso, se nao anotarmos a PedidoRepository com @Repository, o unico
	// metodo de injecao de dependencias seria pelo construtor
	// Essa anotacao indica que esse objeto eh um bean(componente) dele e pede uma
	// instancia
	@Autowired
	private PedidoRepository pedidoRepository;

	// Anotamos com GetMapping e passamos o path para a requisicao bater aqui
	// Colocamos o httpRequest no metodo para podermos passar atributos do backend
	// pro frontend
	// Depois trocamos o httpRequest pelo Model do Spring
	// Depois usamos o conceito ModelView do Spring, assim nao precisamos do
	// parametro model e precisamos mudar o retorno do metodo
	// -------------------------------------------------------------------------------------------
	// As requisições de /home chegaram aqui
	// -------------------------------------------------------------------------------------------
	// Principal retorna os dados do usuário logado
	@GetMapping
	public ModelAndView home(Principal principal) {
		
		// Paginação para retornar a primeira pagina e de 10 em 10 itens
		PageRequest pageable = PageRequest.of(0, 10);

		List<Pedido> pedidos = pedidoRepository.findByStatusOrderByIdDesc(StatusPedido.ENTREGUE, pageable);

		// Passsamos o nome da view na instancia (mapeamento de pastas)
		ModelAndView mv = new ModelAndView("home");

		// Passamos os dados para levar para a view
		mv.addObject("pedidos", pedidos);

		return mv;
	}

	// Método para quando algum erro ocorrer dentro do /home
	// Anotação para qual exceção queremos mapear
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {

		return "redirect:/home";
	}

}
