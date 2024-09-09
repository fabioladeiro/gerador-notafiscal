package br.com.itau.calculadoratributos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import br.com.itau.geradornotafiscal.model.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.service.CalculadoraAliquota;

public class CalculadoraAliquotaTests {

    @InjectMocks
    private CalculadoraAliquota calculadoraAliquota;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnCorrectAliquotaForPessoaFisicaWithValorTotalItensLessThan500() {
        double aliquota = calculadoraAliquota.calcularAliquotaPessoaFisica(400);
        assertEquals(0, aliquota);
    }

    @Test
    public void shouldReturnCorrectAliquotaForPessoaFisicaWithValorTotalItensBetween500And2000() {
        double aliquota = calculadoraAliquota.calcularAliquotaPessoaFisica(1500);
        assertEquals(0.12, aliquota);
    }

    @Test
    public void shouldReturnCorrectAliquotaForPessoaFisicaWithValorTotalItensBetween2000And3500() {
        double aliquota = calculadoraAliquota.calcularAliquotaPessoaFisica(2500);
        assertEquals(0.15, aliquota);
    }

    @Test
    public void shouldReturnCorrectAliquotaForPessoaFisicaWithValorTotalItensGreaterThan3500() {
        double aliquota = calculadoraAliquota.calcularAliquotaPessoaFisica(4000);
        assertEquals(0.17, aliquota);
    }

    @Test
    public void shouldReturnCorrectAliquotaForPessoaJuridicaWithSimplesNacionalAndValorTotalItensLessThan1000() {
        double aliquota = calculadoraAliquota.calcularAliquotaPessoaJuridica(900, RegimeTributacaoPJ.SIMPLES_NACIONAL);
        assertEquals(0.03, aliquota);
    }

    @Test
    public void shouldReturnCorrectAliquotaForPessoaJuridicaWithLucroPresumidoAndValorTotalItensBetween1000And2000() {
        double aliquota = calculadoraAliquota.calcularAliquotaPessoaJuridica(1500, RegimeTributacaoPJ.LUCRO_PRESUMIDO);
        assertEquals(0.09, aliquota);
    }

    @Test
    public void shouldThrowExceptionForUnknownRegimeTributacao() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculadoraAliquota.calcularAliquotaPessoaJuridica(1000, null);
        });
    }
    
}
