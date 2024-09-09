package br.com.itau.geradornotafiscal.exception;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErroResposta {
    private String mensagem;
    private int codigoErro;

}