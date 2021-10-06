package br.com.alura.mvc.mudi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.alura.mvc.mudi.dto.RequisicaoNovoPedido;
import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.User;
import br.com.alura.mvc.mudi.repository.PedidoRepository;
import br.com.alura.mvc.mudi.repository.UserRepository;

// Trata todas as requisicoes do pedido
@Controller
// Um mapping tanto para get quanto para post
// Tudo que for /pedido cai aqui neste controller
@RequestMapping("pedido")
public class PedidoController {

	// Da uma instancia (injecao de dependencias)
	@Autowired
	private PedidoRepository pedidoRepository;

	// Da uma instancia (injecao de dependencias)
	@Autowired
	private UserRepository userRepository;

	// /pedido/formulario cai aqui
	// Para nao dar erro quando o usuario entra pela primeira vez no formulario
	// temos que passar o mesmo objeto de requisicao
	@GetMapping("formulario")
	public ModelAndView formulario(RequisicaoNovoPedido requisicao) {

		ModelAndView mv = new ModelAndView("pedido/formulario");

		return mv;
	}

	// Passando post pois o formulario de cadastro de pedido envia por post
	// O parametro do metodo eh uma classe DTO que o Spring instancia com os dados
	// do formulario
	// Os atributos dessa DTO devem ter o mesmo nome dos names dos inputs
	@PostMapping("novo")
	// A anotacao VALID eh para linkar o Spring com a validacao dos atributos da
	// classe DTO,
	// com ela o Spring retorna um Binding Result informando se a validacao deu
	// certo
	public ModelAndView novo(@Valid RequisicaoNovoPedido requisicao, BindingResult result) {
		
		if (result.hasErrors()) {
			return new ModelAndView("pedido/formulario");
		}

		// Aqui o Spring faz um redirect (visto que ja estamos dentro da /formulario
		// Esse redirect é client side (resposta com requisição HTTP)
		// Para fazer um redirect server side devemos usar 'forward', assim mantemos a
		// mesma requisição
		// ---------------------------------------------------------------------------------------------
		// Nesse exemplo, o melhor seria usar redirect, pois estamos trabalhando com uma
		// requisição POST, seguindo a regra: "always redirect after post".
		ModelAndView mv = new ModelAndView("redirect:/home");

		// Spring nos fornece informações sobre o usuário logado
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(username);
		
		// Metodo padrao que eh criado para conversao do DTO em um pedido
		Pedido pedido = requisicao.toPedido();
		pedido.setUser(user);

		pedidoRepository.save(pedido);

		return mv;
	} 

}
