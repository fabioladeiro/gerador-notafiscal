package br.com.itau.calculadoratributos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import br.com.itau.geradornotafiscal.model.Endereco;
import br.com.itau.geradornotafiscal.model.Finalidade;
import br.com.itau.geradornotafiscal.model.Regiao;
import br.com.itau.geradornotafiscal.service.impl.CalculadoraFrete;

public class CalculadoraFreteTests {

    @InjectMocks
    private CalculadoraFrete calculadoraFrete;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void shouldApplyNorthRegionPercentualAndRoundToOneDecimal() {
        Endereco endereco = new Endereco();
        endereco.setFinalidade(Finalidade.ENTREGA);
        endereco.setRegiao(Regiao.NORTE);

        List<Endereco> enderecos = Arrays.asList(endereco);
        double valorFrete = 100.0;

        double valorFreteCalculado = calculadoraFrete.calcularValorFreteComPercentual(valorFrete, enderecos);

        assertEquals(108.0, valorFreteCalculado);
    }

    @Test
    public void shouldApplyNortheastRegionPercentualAndRoundToOneDecimal() {
        Endereco endereco = new Endereco();
        endereco.setFinalidade(Finalidade.COBRANCA_ENTREGA);
        endereco.setRegiao(Regiao.NORDESTE);

        List<Endereco> enderecos = Arrays.asList(endereco);
        double valorFrete = 100.0;

        double valorFreteCalculado = calculadoraFrete.calcularValorFreteComPercentual(valorFrete, enderecos);

        assertEquals(108.5, valorFreteCalculado);
    }

    @Test
    public void shouldApplyCentroOesteRegionPercentualAndRoundToOneDecimal() {
        Endereco endereco = new Endereco();
        endereco.setFinalidade(Finalidade.ENTREGA);
        endereco.setRegiao(Regiao.CENTRO_OESTE);

        List<Endereco> enderecos = Arrays.asList(endereco);
        double valorFrete = 100.0;

        double valorFreteCalculado = calculadoraFrete.calcularValorFreteComPercentual(valorFrete, enderecos);

        assertEquals(107.0, valorFreteCalculado);
    }

    @Test
    public void shouldApplySoutheastRegionPercentualAndRoundToOneDecimal() {
        Endereco endereco = new Endereco();
        endereco.setFinalidade(Finalidade.COBRANCA_ENTREGA);
        endereco.setRegiao(Regiao.SUDESTE);

        List<Endereco> enderecos = Arrays.asList(endereco);
        double valorFrete = 100.0;

        double valorFreteCalculado = calculadoraFrete.calcularValorFreteComPercentual(valorFrete, enderecos);

        assertEquals(104.8, valorFreteCalculado);
    }

    @Test
    public void shouldApplySouthRegionPercentualAndRoundToOneDecimal() {
        Endereco endereco = new Endereco();
        endereco.setFinalidade(Finalidade.ENTREGA);
        endereco.setRegiao(Regiao.SUL);

        List<Endereco> enderecos = Arrays.asList(endereco);
        double valorFrete = 100.0;

        double valorFreteCalculado = calculadoraFrete.calcularValorFreteComPercentual(valorFrete, enderecos);

        assertEquals(106.0, valorFreteCalculado);
    }

    @Test
    public void shouldReturnOriginalFreteWhenNoMatchingRegion() {
        Endereco endereco = new Endereco();
        endereco.setFinalidade(Finalidade.COBRANCA);
        endereco.setRegiao(null); // Sem região correspondente

        List<Endereco> enderecos = Arrays.asList(endereco);
        double valorFrete = 100.0;

        double valorFreteCalculado = calculadoraFrete.calcularValorFreteComPercentual(valorFrete, enderecos);

        assertEquals(100.0, valorFreteCalculado);
    }
    
}
