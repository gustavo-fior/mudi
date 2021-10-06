package br.com.alura.mvc.mudi.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.StatusPedido;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

// Classe para fazer a comunicação com outra aplicação de front-end (vue.js) via requisições http
@RestController
@RequestMapping("/api/pedidos")
public class PedidosRest {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	// Retorna automaticamente(via biblioteca Jackson) um JSON com os pedidos buscados no banco
	@GetMapping("aguardando")
	public List<Pedido> getPedidosAguardandoOfertas() {
		
		PageRequest pageable = PageRequest.of(0, 10);
		
		return pedidoRepository.findByStatusOrderByIdDesc(StatusPedido.AGUARDANDO, pageable);
		
	}
	
}
