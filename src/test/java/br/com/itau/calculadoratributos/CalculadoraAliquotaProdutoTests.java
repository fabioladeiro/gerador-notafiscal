package br.com.itau.calculadoratributos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import br.com.itau.geradornotafiscal.model.Item;
import br.com.itau.geradornotafiscal.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.service.CalculadoraAliquotaProduto;

class CalculadoraAliquotaProdutoTests {

    @InjectMocks
    private CalculadoraAliquotaProduto calculadoraAliquotaProduto;

	@BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
    public void shouldCalculateAliquotaCorrectlyForMultipleItems() {
        Item item1 = new Item();
        item1.setIdItem("1");
        item1.setDescricao("Item 1");
        item1.setValorUnitario(100.0);
        item1.setQuantidade(2);

        Item item2 = new Item();
        item2.setIdItem("2");
        item2.setDescricao("Item 2");
        item2.setValorUnitario(200.0);
        item2.setQuantidade(1);

        List<Item> items = Arrays.asList(item1, item2);
        double aliquotaPercentual = 0.15;

        List<ItemNotaFiscal> itemNotaFiscalList = calculadoraAliquotaProduto.calcularAliquota(items, aliquotaPercentual);

        assertEquals(2, itemNotaFiscalList.size());
        assertEquals(15.0, itemNotaFiscalList.get(0).getValorTributoItem());
        assertEquals(30.0, itemNotaFiscalList.get(1).getValorTributoItem());
    }

    @Test
    public void shouldReturnEmptyListWhenNoItemsProvided() {
        List<Item> items = Arrays.asList();
        double aliquotaPercentual = 0.15;

        List<ItemNotaFiscal> itemNotaFiscalList = calculadoraAliquotaProduto.calcularAliquota(items, aliquotaPercentual);

        assertTrue(itemNotaFiscalList.isEmpty());
    }

    @Test
    public void shouldCalculateZeroAliquotaForZeroAliquotaPercentual() {
        Item item = new Item();
        item.setIdItem("1");
        item.setDescricao("Item com aliquota zerada");
        item.setValorUnitario(300.0);
        item.setQuantidade(1);

        List<Item> items = Arrays.asList(item);
        double aliquotaPercentual = 0.0;

        List<ItemNotaFiscal> itemNotaFiscalList = calculadoraAliquotaProduto.calcularAliquota(items, aliquotaPercentual);

        assertEquals(1, itemNotaFiscalList.size());
        assertEquals(0.0, itemNotaFiscalList.get(0).getValorTributoItem());
    }

    @Test
    public void shouldCalculateAliquotaForItemsWithDifferentQuantities() {
        Item item = new Item();
        item.setIdItem("1");
        item.setDescricao("Item With Quantity");
        item.setValorUnitario(100.0);
        item.setQuantidade(5);

        List<Item> items = Arrays.asList(item);
        double aliquotaPercentual = 0.12;

        List<ItemNotaFiscal> itemNotaFiscalList = calculadoraAliquotaProduto.calcularAliquota(items, aliquotaPercentual);

        assertEquals(1, itemNotaFiscalList.size());
        assertEquals(12.0, itemNotaFiscalList.get(0).getValorTributoItem());
    }

}
