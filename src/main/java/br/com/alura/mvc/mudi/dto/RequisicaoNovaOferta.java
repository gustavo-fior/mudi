package br.com.alura.mvc.mudi.dto; 

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.alura.mvc.mudi.model.Oferta;

public class RequisicaoNovaOferta {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	// Id do pedido que seria enviado no JSON da API, por isso nao precisa validar
	private Long pedidoId;
	
	@NotNull
	// Começar com digito(\d), ter quantos digitos quiser (+), ter um ponto (.), ter mais 2 digitos (d+{2}),
	// o que está entre parentêses é opcional (?) e finaliza a string ($)
	//(duas barras, pois uma é para escapar a outra)
	@Pattern(regexp = "^\\d+(\\.\\d+{2})?$")
	private String valor;
	
	@NotNull
	@Pattern(regexp = "^\\d+{2}/\\d+{2}/\\d+{4}$")
	private String dataEntrega;
	private String comentario;

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Oferta toOferta() {

		Oferta oferta = new Oferta();

		oferta.setComentario(this.comentario);
		oferta.setDataEntrega(LocalDate.parse(this.dataEntrega, formatter));
		oferta.setValor(new BigDecimal(this.valor));

		return oferta;

	}

}
