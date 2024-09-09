package br.com.itau.geradornotafiscal.exception;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PedidoInvalidoException extends RuntimeException {

    private final ErroResposta erroResposta;

    public PedidoInvalidoException(String message, int codigoErro) {
        super(message);
        this.erroResposta = new ErroResposta(message, codigoErro);
    }

    public PedidoInvalidoException(String message, Throwable cause, int codigoErro) {
        super(message, cause);
        this.erroResposta = new ErroResposta(message, codigoErro);

    }
}
