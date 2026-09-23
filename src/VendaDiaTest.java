import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class VendaDiaTest {
 
    
    @Test 
    public void adicionaProdutoCorretamente(){
        //Arrange
        VendaDia diaVenda = new VendaDia(null);
        Produto novoProduto = new Produto("Refrigerante", 20.0, 0.1, "BEBIDA");
        //Act
        double valorTotalEsperado = diaVenda.adicionarProduto(novoProduto);
        //Assert
        assertEquals(27.5, valorTotalEsperado, 0.01);;
    }

    @Test 
    public void classificaDiaBomCorretamente(){
        //Arrange
        //Act
        //Assert
    }

    @Test 
    public void verificaDiaMelhorQueOOutro(){
        //Arrange
        //Act
        //Assert
    }
}
