package br.com.alura.mvc.mudi.dto;

import javax.validation.constraints.NotBlank;

import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.StatusPedido;

// Classe para receber os dados do formulario de cadastro de novos pedidos
public class RequisicaoNovoPedido {

	// Validacao para o input nao vir nulo ou vazio
	// Se isso acontecer, ocorre um erro do tipo NotBlank.requisicaoNovoPedido.nomeProduto
	// Poderiamos passar a mensagem de erro deste atributo na anotacao, porem fizemos
	// por meio do arquivo messages.properties
	@NotBlank
	private String nomeProduto;
	
	@NotBlank
	private String urlProduto;
	
	// Outros exemplos de anotacoes de validacao: @Email, @Min, @Max, @Pattern
	@NotBlank
	private String urlImagem;
	private String descricaoProduto;

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getUrlProduto() {
		return urlProduto;
	}

	public void setUrlProduto(String urlProduto) {
		this.urlProduto = urlProduto;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public Pedido toPedido() {
		
		Pedido pedido = new Pedido();
		pedido.setNomeProduto(nomeProduto);
		pedido.setDescricao(descricaoProduto);
		pedido.setUrlDaImagem(urlImagem);
		pedido.setUrlDoProduto(urlProduto);
		pedido.setStatus(StatusPedido.AGUARDANDO);
		
		return pedido;
	}

}
