/** 
* MIT License
*
* Copyright(c) 26 João Caram <caram@pucminas.br>
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;


public class VendaDia {

    //#region atributos
    private LocalDate data;
    private List<Produto> produtos;
    //#endregion

    //#region construtores
    /**
     * Cria uma venda vazia para a data especificada. Se a data for nula, a venda
     * será criada para a data de hoje
     * @param data Data para a venda
     */
    public VendaDia(LocalDate data){
        if(data == null)
            data = LocalDate.now();
        this.data = data;
        produtos = new LinkedList<>();
    }
    //#endregion

    //#region métodos
    /**
     * Tenta adicionar uma venda para o dia. O produto só será aceito se não for nulo. 
     * @param produto Produto a ser adicionado (não nulo)
     * @return Valor total das vendas do dia após a tentativa de inclusão
     */
    public double adicionarProduto(Produto produto){
        if(produto != null){
            produtos.add(produto);
        }
        return faturamento();
    }

    /**
     * Valor dos impostos arrecadados neste dia de vendas.
     * @return Double positivo com os impostos arrecadados neste dia de vendas.
     */
    public double valorImpostos(){
        double impostos = 0d;
        for (Produto produto : produtos) {
            impostos += produto.valorImposto();
        }
        return impostos;
    }

    /**
     * Valor total faturado (recebido) neste dia de vendas.
     * @return Double positivo com a soma de todas as vendas de produtos do dia.
     */
    public double faturamento(){
        double venda = 0d;
        for (Produto produto : produtos) {
            venda += produto.valorVenda();
        }
        return venda;
    }

    private int numClassificacao() {
        String classificacao = classificacao();
        Integer num = switch (classificacao) {
            case "Ótimo" -> 5;
            case "Bom" -> 4;
            case "Regular" -> 3;
            case "Ruim" -> 2;
            default -> 1;
        };
        return num;
    }
    /**
     * Indica se este dia de vendas foi melhor do que outro, de acordo com a classificação
     * em faixas da mercearia.
     * @param outroDia Dia a ser comparado com este
     * @return TRUE caso este dia tenha sido melhor, FALSE caso contrário.
     */
    public boolean melhorQue(VendaDia outroDia){
        boolean melhor = false;
        int liquidoHoje = this.numClassificacao();
        int liquidoOutroDia = outroDia.numClassificacao();
        if (liquidoHoje > liquidoOutroDia) {
            melhor = true;
        }
        return melhor;
    }

    /**
     * Classificação do dia de vendas, de acordo com o especificado no exercício.
     * @return Uma string de uma única palavra indicando a classificação deste dia.
     */
    public String classificacao(){
        double liquidoString = this.faturamento() - this.valorImpostos();
        String classificacao = "";
        if (liquidoString >= 1500d) {
            classificacao = "Ótimo";
        }
        if (liquidoString >= 1000d || liquidoString < 1500d) {
            classificacao = "Bom";
        }
        if (liquidoString >= 400d || liquidoString < 1000d) {
            classificacao = "Regular";
        }
        if (liquidoString >= 100d || liquidoString < 400d) {
            classificacao = "Ruim";
        }
        if (liquidoString < 100d) {
            classificacao = "Péssimo";
        }
        return classificacao;
    }

    /**
     * Cria um cupom fiscal para o dia. Informa sua data, os detalhes de cada produto vendido e,
     * ao final, o faturamento, a classificação do dia e o valor a recolher em impostos.
     * @return String multilinhas com as informações descritas
     */
    public String cupomFiscal(){
        String separador ="\n---------------------------\n"; 
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        StringBuilder cupom = new StringBuilder("Vendas de "+df.format(data));
        cupom.append(separador);
        int i = 1;
        for (Produto produto : produtos) {
            cupom.append(String.format("%02d - %s\n", i, produto.cupomVenda()));
        }
        cupom.append(separador);
        cupom.append(String.format("Total do dia: R$ %.2f\n",faturamento()));
        cupom.append(String.format("(Dia %s)\n",classificacao()));
        cupom.append(String.format("Impostos recolhidos: R$ %.2f",valorImpostos()));
        return cupom.toString();        
    }
    //#endregion
}   