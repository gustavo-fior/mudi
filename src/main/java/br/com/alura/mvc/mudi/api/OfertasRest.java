package br.com.alura.mvc.mudi.api;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.mvc.mudi.dto.RequisicaoNovaOferta;
import br.com.alura.mvc.mudi.model.Oferta;
import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

@RestController
@RequestMapping("/api/ofertas")
public class OfertasRest {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	// Usamos post para o envio dos dados da nova oferta para este metodo
	// @RequestBody eh para linkar os atributos passados pela requisicao do vue.js a essa requisicao
	@PostMapping
	public Oferta criaOferta(@Valid @RequestBody RequisicaoNovaOferta requisicao) {
		
		// Pedido buscado com base no id passado dentro da requisicao
		Optional<Pedido> pedidoOptional = pedidoRepository.findById(requisicao.getPedidoId());
		
		if (!pedidoOptional.isPresent()) {
			return null;
		}
		
		Pedido pedido = pedidoOptional.get();
		
		Oferta oferta = requisicao.toOferta();
		oferta.setPedido(pedido);
		pedido.getOfertas().add(oferta);
		
		// Vai atualizar o pedido com a oferta feita e isso eh feito automaticamente por causa do cascade
		pedidoRepository.save(pedido);
		
		return oferta;
		
	}
	
}
