package br.com.itau.geradornotafiscal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Item {
	 	@JsonProperty("id_item")
		@NotBlank(message = "O id_item não pode estar em branco.")
	    private String idItem;

	    @JsonProperty("descricao")
		@NotBlank(message = "A descrição do item não pode estar em branco.")
	    private String descricao;

	    @JsonProperty("valor_unitario")
		@DecimalMin(value = "0.01", message = "O valor unitário do item deve ser maior que 0.")
	    private double valorUnitario;

	    @JsonProperty("quantidade")
		@Min(value = 1, message = "A quantidade do item deve ser pelo menos 1.")
	    private int quantidade;

}

