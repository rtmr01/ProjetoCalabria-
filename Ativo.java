package br.com.cesarschool.poo.titulos.entidades;
import java.time.LocalDate;

/*
 * Esta classe deve ter os seguintes atributos:
 * identificador, do tipo int
 * nome, do tipo String
 * data de validade, do tipo LocalDate
 * 
 * Deve ter um construtor público que inicializa os atributos, 
 * e métodos set/get públicos para os atributos. O atributo identificador
 * é read-only fora da classe.
 */
public class Ativo {
private int identificador;
private String nome;
private LocalDate dataDeValidade;

public Ativo(int identificador, String nome, LocalDate dataDeValidade){
    this.identificador= identificador;
    this.nome=nome;
    this.dataDeValidade=dataDeValidade;
}

public int getIdentificador(){
    return identificador;
}
public String getNome(){
    return nome;
}
public void setNome(String nome){
    this.nome=nome;

}

public LocalDate getDataDeValidade(){
    return dataDeValidade;
}

public void setDataDeValidade(LocalDate dataDeValidade){
    this.dataDeValidade=dataDeValidade;
}
}
