package br.com.itau.geradornotafiscal.service.impl;

import br.com.itau.geradornotafiscal.model.Endereco;
import br.com.itau.geradornotafiscal.model.Finalidade;
import br.com.itau.geradornotafiscal.model.Regiao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalculadoraFrete {
    
    public double calcularValorFreteComPercentual(double valorFrete, List<Endereco> enderecos) {

		Regiao regiao = enderecos.stream()
				.filter(endereco -> endereco.getFinalidade() == Finalidade.ENTREGA || endereco.getFinalidade() == Finalidade.COBRANCA_ENTREGA)
				.map(Endereco::getRegiao)
				.findFirst()
				.orElse(null);

		double percentual = 1.0;

		if (regiao == Regiao.NORTE) percentual = 1.08;
        else if (regiao == Regiao.NORDESTE) percentual = 1.085;
        else if (regiao == Regiao.CENTRO_OESTE) percentual = 1.07;
        else if (regiao == Regiao.SUDESTE) percentual = 1.048;
        else if (regiao == Regiao.SUL) percentual = 1.06;

		double valorFreteCalculado = valorFrete * percentual;

        // Arredondando para 2 casas decimais
        BigDecimal bd = new BigDecimal(valorFreteCalculado).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
