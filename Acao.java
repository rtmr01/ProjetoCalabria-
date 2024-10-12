package br.com.cesarschool.poo.titulos.entidades;
import java.time.LocalDate;
/*
 * Esta classe deve herdar de Ativo.
 * E deve ter os seguintes atributos:
 * valorUnitario, do tipo double.
 *
 * Deve ter um construtor público que inicializa os atributos,
 * e métodos set/get públicos para os atributos.
 *
 * Deve ter um metodo público double calcularPrecoTransacao(double montante): o preço
 * da transação é o produto do montante pelo valorUnitario.
 */
public class Acao extends Ativo{
double valorUnitario;

public Acao(int identificador, String nome, LocalDate dataDeValidade,double valorUnitario){
    super(identificador,nome,dataDeValidade);
    this. valorUnitario=valorUnitario;
}

    public double getValorUnitario() {
        return valorUnitario;
    }
    public void setValorUnitario(double valorUnitario){
    this.valorUnitario=valorUnitario;
    }

    public double calcularPrecoTransacao(double montante) {
        return montante * valorUnitario;
    }
}
