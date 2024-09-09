package br.com.itau.geradornotafiscal.service.impl;

import br.com.itau.geradornotafiscal.model.*;
import br.com.itau.geradornotafiscal.service.CalculadoraAliquota;
import br.com.itau.geradornotafiscal.service.CalculadoraAliquotaProduto;
import br.com.itau.geradornotafiscal.service.GeradorNotaFiscalService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class GeradorNotaFiscalServiceImpl implements GeradorNotaFiscalService{
	@Override
	public NotaFiscal gerarNotaFiscal(Pedido pedido) {

		Destinatario destinatario = pedido.getDestinatario();
		TipoPessoa tipoPessoa = destinatario.getTipoPessoa();
		List<ItemNotaFiscal> itemNotaFiscalList = new ArrayList<>();
		CalculadoraAliquotaProduto calculadoraAliquotaProduto = new CalculadoraAliquotaProduto();
		CalculadoraFrete calculadoraFrete = new CalculadoraFrete();
		CalculadoraAliquota calculadoraAliquota = new CalculadoraAliquota();
		RegimeTributacaoPJ regimeTributacaoPJ = destinatario.getRegimeTributacao();
		List<Item> itensPedido = pedido.getItens();
		List<Endereco> enderecos = destinatario.getEnderecos();
		double valorTotalItensCalculado = pedido.calcularValorTotalItens();
		double valorFrete = pedido.getValorFrete();
		

		// VERIFICA TIPO PESSOA E CALCULA A TRIBUTACAO
		double aliquota = calculadoraAliquota.calcularAliquota(tipoPessoa, valorTotalItensCalculado, regimeTributacaoPJ);
		itemNotaFiscalList = calculadoraAliquotaProduto.calcularAliquota(itensPedido, aliquota);

		// calculo Frete
		double valorFreteComPercentual = calculadoraFrete.calcularValorFreteComPercentual(valorFrete, enderecos);

		// criacao do objeto da nota fiscal
		String idNotaFiscal = UUID.randomUUID().toString();

		NotaFiscal notaFiscal = NotaFiscal.builder()
				.idNotaFiscal(idNotaFiscal)
				.data(LocalDateTime.now())
				.valorTotalItens(valorTotalItensCalculado)
				.valorFrete(valorFreteComPercentual)
				.itens(itemNotaFiscalList)
				.destinatario(destinatario)
				.build();

		new EstoqueService().enviarNotaFiscalParaBaixaEstoque(notaFiscal);
		new RegistroService().registrarNotaFiscal(notaFiscal);
		new EntregaService().agendarEntrega(notaFiscal);
		new FinanceiroService().enviarNotaFiscalParaContasReceber(notaFiscal);

		return notaFiscal;
	}
}