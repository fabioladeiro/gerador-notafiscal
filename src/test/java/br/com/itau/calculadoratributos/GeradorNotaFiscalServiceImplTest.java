package br.com.itau.calculadoratributos;

import br.com.itau.geradornotafiscal.model.*;
import br.com.itau.geradornotafiscal.service.CalculadoraAliquota;
import br.com.itau.geradornotafiscal.service.CalculadoraAliquotaProduto;
import br.com.itau.geradornotafiscal.service.impl.GeradorNotaFiscalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GeradorNotaFiscalServiceImplTest {

    @InjectMocks
    private GeradorNotaFiscalServiceImpl geradorNotaFiscalService;

    @Mock
    private CalculadoraAliquotaProduto calculadoraAliquotaProduto;

    @Mock
    private CalculadoraAliquota calculadoraAliquota;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void shouldGenerateNotaFiscalForTipoPessoaFisicaWithValorTotalItensLessThan500() {
        Pedido pedido = new Pedido();
        pedido.setValorTotalItens(400);
        pedido.setValorFrete(100);
        Destinatario destinatario = new Destinatario();
        destinatario.setTipoPessoa(TipoPessoa.FISICA);

        // Create and add Endereco to the Destinatario
        Endereco endereco = new Endereco();
        endereco.setFinalidade(Finalidade.ENTREGA);
        endereco.setRegiao(Regiao.SUDESTE);
        destinatario.setEnderecos(Arrays.asList(endereco));

        pedido.setDestinatario(destinatario);

        // Create and add items to the Pedido
        Item item = new Item();
        item.setValorUnitario(100);
        item.setQuantidade(4);
        pedido.setItens(Arrays.asList(item));

        when(calculadoraAliquota.calcularAliquota(TipoPessoa.FISICA, 400, null)).thenReturn(0.0);
        when(calculadoraAliquotaProduto.calcularAliquota(pedido.getItens(), 0.0)).thenReturn(Arrays.asList(new ItemNotaFiscal()));

        NotaFiscal notaFiscal = geradorNotaFiscalService.gerarNotaFiscal(pedido);

        assertEquals(pedido.getValorTotalItens(), notaFiscal.getValorTotalItens());
        assertEquals(1, notaFiscal.getItens().size());
        assertEquals(0, notaFiscal.getItens().get(0).getValorTributoItem());
    }

    @Test
    public void shouldGenerateNotaFiscalForTipoPessoaJuridicaWithRegimeTributacaoLucroPresumidoAndValorTotalItensGreaterThan5000() {
        Pedido pedido = new Pedido();
        pedido.setValorTotalItens(6000);
        pedido.setValorFrete(100);
        Destinatario destinatario = new Destinatario();
        destinatario.setTipoPessoa(TipoPessoa.JURIDICA);
        destinatario.setRegimeTributacao(RegimeTributacaoPJ.LUCRO_PRESUMIDO);

        // Create and add Endereco to the Destinatario
        Endereco endereco = new Endereco();
        endereco.setFinalidade(Finalidade.ENTREGA);
        endereco.setRegiao(Regiao.SUDESTE);
        destinatario.setEnderecos(Arrays.asList(endereco));

        pedido.setDestinatario(destinatario);

        // Create and add items to the Pedido
        Item item = new Item();
        item.setValorUnitario(1000);
        item.setQuantidade(6);
        pedido.setItens(Arrays.asList(item));

        when(calculadoraAliquota.calcularAliquota(TipoPessoa.JURIDICA, 6000, RegimeTributacaoPJ.LUCRO_PRESUMIDO)).thenReturn(0.20);
        when(calculadoraAliquotaProduto.calcularAliquota(pedido.getItens(), 0.20)).thenReturn(Arrays.asList(new ItemNotaFiscal()));

        NotaFiscal notaFiscal = geradorNotaFiscalService.gerarNotaFiscal(pedido);

        assertEquals(pedido.getValorTotalItens(), notaFiscal.getValorTotalItens());
        assertEquals(1, notaFiscal.getItens().size());
        assertEquals(0.20 * item.getValorUnitario(), notaFiscal.getItens().get(0).getValorTributoItem());
    }

    @Test
    public void shouldHandleNotaFiscalWithZeroItemValue() {
        // Testar nota fiscal com valor total de itens igual a 0
        Pedido pedido = new Pedido();
        pedido.setValorTotalItens(0);
        pedido.setValorFrete(50);
        Destinatario destinatario = new Destinatario();
        destinatario.setTipoPessoa(TipoPessoa.FISICA);

        Endereco endereco = new Endereco();
        endereco.setFinalidade(Finalidade.ENTREGA);
        endereco.setRegiao(Regiao.SUDESTE);
        destinatario.setEnderecos(Arrays.asList(endereco));

        pedido.setDestinatario(destinatario);

        Item item = new Item();
        item.setValorUnitario(0);
        item.setQuantidade(1);
        pedido.setItens(Arrays.asList(item));

        NotaFiscal notaFiscal = geradorNotaFiscalService.gerarNotaFiscal(pedido);

        assertEquals(0, notaFiscal.getValorTotalItens());
        assertEquals(0, notaFiscal.getItens().get(0).getValorTributoItem());
    }

    @Test
    public void shouldHandleNotaFiscalWithZeroFrete() {
        // Testar frete com valor zero
        Pedido pedido = new Pedido();
        pedido.setValorTotalItens(1000);
        pedido.setValorFrete(0);
        Destinatario destinatario = new Destinatario();
        destinatario.setTipoPessoa(TipoPessoa.FISICA);

        Endereco endereco = new Endereco();
        endereco.setFinalidade(Finalidade.ENTREGA);
        endereco.setRegiao(Regiao.SUDESTE);
        destinatario.setEnderecos(Arrays.asList(endereco));

        pedido.setDestinatario(destinatario);

        Item item = new Item();
        item.setValorUnitario(1000);
        item.setQuantidade(1);
        pedido.setItens(Arrays.asList(item));

        NotaFiscal notaFiscal = geradorNotaFiscalService.gerarNotaFiscal(pedido);

        assertEquals(1000, notaFiscal.getValorTotalItens());
        assertEquals(0, notaFiscal.getValorFrete());
    }

    @Test
    public void shouldValidateAndCorrectWrongValorTotalItens() {
        // Criação do pedido com valor total errado no payload
        Pedido pedido = new Pedido();
        pedido.setValorTotalItens(5000); // Valor errado propositalmente
        pedido.setValorFrete(100);
        Destinatario destinatario = new Destinatario();
        destinatario.setTipoPessoa(TipoPessoa.FISICA);

        Endereco endereco = new Endereco();
        endereco.setFinalidade(Finalidade.ENTREGA);
        endereco.setRegiao(Regiao.SUDESTE);
        destinatario.setEnderecos(Arrays.asList(endereco));

        pedido.setDestinatario(destinatario);

        // Itens com valores corretos (o total deve ser 4000, não 5000)
        Item item1 = new Item();
        item1.setValorUnitario(1000);
        item1.setQuantidade(2);

        Item item2 = new Item();
        item2.setValorUnitario(500);
        item2.setQuantidade(4);

        pedido.setItens(Arrays.asList(item1, item2));

        // Gera a nota fiscal com base nos itens e quantidades, não no valor total setado no pedido
        NotaFiscal notaFiscal = geradorNotaFiscalService.gerarNotaFiscal(pedido);

        // Valida que o valor total da nota fiscal é o valor correto (calculado com base nos itens)
        double valorEsperadoItens = (item1.getValorUnitario() * item1.getQuantidade()) + (item2.getValorUnitario() * item2.getQuantidade());
        assertEquals(valorEsperadoItens, notaFiscal.getValorTotalItens());
    }


}