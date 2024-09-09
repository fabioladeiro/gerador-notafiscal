package br.com.itau.geradornotafiscal.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.*;


@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Pedido {
	 	@JsonProperty("id_pedido")
		@Min(value = 1, message = "O id_pedido deve ser um número inteiro positivo.")
	    private int idPedido;

	    @JsonProperty("data")
		@NotNull(message = "A data do pedido não pode ser nula.")
	    private LocalDate data;

	    @JsonProperty("valor_total_itens")
		@Positive(message = "O valor total dos itens deve ser maior que zero.")
	    private double valorTotalItens;

	    @JsonProperty("valor_frete")
		@DecimalMin(value = "0", message = "O valor do frete não pode ser negativo.")
	    private double valorFrete;

	    @JsonProperty("itens")
		@NotEmpty(message = "A lista de itens não pode ser vazia.")
    	@Valid
	    private List<@Valid Item> itens;

	    @JsonProperty("destinatario")
		@NotNull(message = "O destinatário não pode ser nulo.")
    	@Valid
	    private Destinatario destinatario;

		public double calcularValorTotalItens() {
			return itens.stream()
					.mapToDouble(item -> item.getValorUnitario() * item.getQuantidade())
					.sum();
		}

}
