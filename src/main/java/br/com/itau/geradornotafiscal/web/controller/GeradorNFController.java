package br.com.itau.geradornotafiscal.web.controller;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.itau.geradornotafiscal.model.Pedido;
import br.com.itau.geradornotafiscal.service.GeradorNotaFiscalService;
import br.com.itau.geradornotafiscal.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pedido")
public class GeradorNFController {

	private static final Logger logger = LoggerFactory.getLogger(GeradorNFController.class);

	@Autowired
	private GeradorNotaFiscalService notaFiscalService;

	@PostMapping("/gerarNotaFiscal")
	public ResponseEntity<?> gerarNotaFiscal(@Valid @RequestBody Pedido pedido) {

		System.out.println("cheguei");

		try {
			
			// Verifica se o pedido é válido antes de gerar a nota fiscal
			if (pedido == null || pedido.getItens() == null || pedido.getItens().isEmpty()) {
				throw new PedidoInvalidoException("Pedido inválido: o pedido deve conter itens.", 400);
			}

			// Verifica se o valor total dos itens é válido antes de gerar a nota fiscal
			if (pedido.getValorTotalItens() != pedido.calcularValorTotalItens()) {
				throw new PedidoInvalidoException("Pedido inválido: o valor total dos itens está inconsistente.", 400);
			}
			
			NotaFiscal notaFiscal = notaFiscalService.gerarNotaFiscal(pedido);
			logger.info("Nota fiscal gerada com sucesso para o pedido: {}", pedido.getIdPedido());
			return new ResponseEntity<>(notaFiscal, HttpStatus.OK);
		} catch (PedidoInvalidoException e) {
			logger.warn("Pedido inválido: {}", e.getMessage());
			return new ResponseEntity<>("Erro: Pedido inválido", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Erro ao gerar nota fiscal: ", e);
			return new ResponseEntity<>("Erro: Erro interno ao gerar nota fiscal", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
