package br.com.itau.geradornotafiscal.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Documento {

    @JsonProperty("numero")
    @NotNull(message = "Número não pode ser nulo")
    private String numero;

    @JsonProperty("tipo")
    @NotNull(message = "Tipo não pode ser nulo")
    private TipoDocumento tipo;

}
