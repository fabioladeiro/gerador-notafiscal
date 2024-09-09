package br.com.itau.geradornotafiscal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Destinatario {
	@JsonProperty("nome")
	@NotBlank(message = "O nome do destinatário não pode estar em branco.")
	private String nome;

	@JsonProperty("tipo_pessoa")
	@NotNull(message = "O tipo_pessoa é obrigatório.")
	private TipoPessoa tipoPessoa;

	@JsonProperty("regime_tributacao")
	@NotNull(message = "O regime_tributacao é obrigatório.")
	private RegimeTributacaoPJ regimeTributacao;

	@JsonProperty("documentos")
	@NotEmpty(message = "A lista de documentos não pode ser vazia.")
    @Valid
	private List<@Valid Documento> documentos;

	@JsonProperty("enderecos")
	@NotEmpty(message = "A lista de endereços não pode ser vazia.")
    @Valid
	private List<@Valid Endereco> enderecos;

}




