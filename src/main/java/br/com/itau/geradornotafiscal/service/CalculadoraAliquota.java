package br.com.itau.geradornotafiscal.service;

import br.com.itau.geradornotafiscal.model.*;

public class CalculadoraAliquota {

    public double calcularAliquota(TipoPessoa tipoPessoa, double valorTotalItensCalculado, RegimeTributacaoPJ regimeTributacao) {
        double aliquota = tipoPessoa == TipoPessoa.FISICA
        ? calcularAliquotaPessoaFisica(valorTotalItensCalculado)
        : calcularAliquotaPessoaJuridica(valorTotalItensCalculado, regimeTributacao);
        
        return aliquota;
    }

    public double calcularAliquotaPessoaFisica(double valorTotalItens) {
        if (valorTotalItens < 500) return 0;
        if (valorTotalItens <= 2000) return 0.12;
        if (valorTotalItens <= 3500) return 0.15;
        return 0.17;
    }

     public double calcularAliquotaPessoaJuridica(double valorTotalItens, RegimeTributacaoPJ regimeTributacao) {
        if (regimeTributacao == RegimeTributacaoPJ.SIMPLES_NACIONAL) {
            if (valorTotalItens < 1000) return 0.03;
            if (valorTotalItens <= 2000) return 0.07;
            if (valorTotalItens <= 5000) return 0.13;
            return 0.19;
        } else if (regimeTributacao == RegimeTributacaoPJ.LUCRO_REAL) {
            if (valorTotalItens < 1000) return 0.03;
            if (valorTotalItens <= 2000) return 0.09;
            if (valorTotalItens <= 5000) return 0.15;
            return 0.20;
        } else if (regimeTributacao == RegimeTributacaoPJ.LUCRO_PRESUMIDO) {
            if (valorTotalItens < 1000) return 0.03;
            if (valorTotalItens <= 2000) return 0.09;
            if (valorTotalItens <= 5000) return 0.16;
            return 0.20;
        }
        throw new IllegalArgumentException("Regime de tributação desconhecido.");
    }
    
}
