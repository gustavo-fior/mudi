package br.com.alura.mvc.mudi.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.StatusPedido;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

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
	@GetMapping("pedido")
	public ModelAndView home(Principal principal) {

		List<Pedido> pedidos = pedidoRepository.findByUsuario(principal.getName());

		// Passsamos o nome da view na instancia (mapeamento de pastas)
		ModelAndView mv = new ModelAndView("usuario/home");

		// Passamos os dados para levar para a view
		mv.addObject("pedidos", pedidos);

		return mv;
	}

	// Agora podemos receber o path pela variável do método
	// A anotação diz para o Spring que a variável status vair vir do path depois de
	// /home/'aqui vem o status'
	@GetMapping("pedido/{status}")
	public ModelAndView status(@PathVariable("status") String status, Principal principal) {

		// Convertendo de String para Enum 
		StatusPedido statusPedido = StatusPedido.valueOf(status.toUpperCase());
		String username = principal.getName();
		
		List<Pedido> pedidos = pedidoRepository.findByStatusEUsuario(statusPedido, username);

		// Passsamos o nome da view na instancia
		ModelAndView mv = new ModelAndView("usuario/home");

		// Passamos os dados para levar para a view
		mv.addObject("pedidos", pedidos);
		mv.addObject("status", status);

		return mv;
	}

	// Método para quando algum erro ocorrer dentro do /home (procurar por algo invalido ou inexistente)
	// Anotação para qual exceção queremos mapear
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {

		return "redirect:/usuario/home";
	}

}
