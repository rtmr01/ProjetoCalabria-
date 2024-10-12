package br.com.cesarschool.poo.titulos.repositorios;
/*
 * Deve gravar em e ler de um arquivo texto chamado Acao.txt os dados dos objetos do tipo
 * Acao. Seguem abaixo exemplos de linhas.
 *
    1;PETROBRAS;2024-12-12;30.33
    2;BANCO DO BRASIL;2026-01-01;21.21
    3;CORREIOS;2027-11-11;6.12 
 * 
 * A inclus�o deve adicionar uma nova linha ao arquivo. N�o � permitido incluir 
 * identificador repetido. Neste caso, o metodo deve retornar false. Inclus�o com
 * sucesso, retorno true.
 * 
 * A altera��o deve substituir a linha atual por uma nova linha. A linha deve ser 
 * localizada por identificador que, quando n�o encontrado, enseja retorno false. 
 * Altera��o com sucesso, retorno true.  
 *   
 * A exclus�o deve apagar a linha atual do arquivo. A linha deve ser 
 * localizada por identificador que, quando n�o encontrado, enseja retorno false. 
 * Exclus�o com sucesso, retorno true.
 * 
 * A busca deve localizar uma linha por identificador, materializar e retornar um 
 * objeto. Caso o identificador n�o seja encontrado no arquivo, retornar null.   
 */

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import java.io.*;
import java.util.*;

public class RepositorioEntidadeOperadora {

    private final String arquivo = "EntidadeOperadora.txt";

    // Create - Adiciona uma nova EntidadeOperadora
    public boolean create(EntidadeOperadora entidade) {
        if (read(entidade.getIdentificador()) != null) {
            return false; // Se a entidade j� existe, n�o adiciona
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(entidade.getIdentificador() + ";" +
                    entidade.getNome() + ";" +
                    entidade.getAutorizadoAcao() + ";" +
                    entidade.getSaldoAcao() + ";" +
                    entidade.getSaldoTituloDivida());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read - L� e retorna uma EntidadeOperadora pelo identificador
    public EntidadeOperadora read(long identificador) {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (Long.parseLong(dados[0]) == identificador) {
                    return new EntidadeOperadora(
                            Long.parseLong(dados[0]),
                            dados[1],
                            Double.parseDouble(dados[2]),
                            Double.parseDouble(dados[3]),
                            Double.parseDouble(dados[4])
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Se a entidade n�o for encontrada
    }

    // Read - Imprime todas as EntidadeOperadora no arquivo
    public void readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update - Atualiza uma EntidadeOperadora existente
    public boolean update(EntidadeOperadora entidadeAtualizada) {
        List<EntidadeOperadora> entidades = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                long id = Long.parseLong(dados[0]);
                if (id == entidadeAtualizada.getIdentificador()) {
                    entidades.add(entidadeAtualizada); // Atualiza a entidade
                    found = true;
                } else {
                    entidades.add(new EntidadeOperadora(
                            id, dados[1], Double.parseDouble(dados[2]),
                            Double.parseDouble(dados[3]), Double.parseDouble(dados[4])
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            writeAll(entidades); // Reescreve o arquivo com a lista atualizada
            return true;
        }
        return false; // Se a entidade n�o foi encontrada
    }

    // Delete - Remove uma EntidadeOperadora existente
    public boolean delete(long identificador) {
        List<EntidadeOperadora> entidades = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                long id = Long.parseLong(dados[0]);
                if (id != identificador) {
                    entidades.add(new EntidadeOperadora(
                            id, dados[1], Double.parseDouble(dados[2]),
                            Double.parseDouble(dados[3]), Double.parseDouble(dados[4])
                    ));
                } else {
                    found = true; // Se encontrou a entidade, n�o a adiciona na nova lista
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            writeAll(entidades); // Reescreve o arquivo sem a entidade deletada
            return true;
        }
        return false; // Se a entidade n�o foi encontrada
    }

    // M�todo auxiliar para reescrever todas as entidades no arquivo
    private void writeAll(List<EntidadeOperadora> entidades) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (EntidadeOperadora entidade : entidades) {
                writer.write(entidade.getIdentificador() + ";" +
                        entidade.getNome() + ";" +
                        entidade.getAutorizadoAcao() + ";" +
                        entidade.getSaldoAcao() + ";" +
                        entidade.getSaldoTituloDivida());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
