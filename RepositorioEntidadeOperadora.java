package br.com.cesarschool.poo.titulos.repositorios;
/*
 * Deve gravar em e ler de um arquivo texto chamado Acao.txt os dados dos objetos do tipo
 * Acao. Seguem abaixo exemplos de linhas.
 *
    1;PETROBRAS;2024-12-12;30.33
    2;BANCO DO BRASIL;2026-01-01;21.21
    3;CORREIOS;2027-11-11;6.12 
 * 
 * A inclusão deve adicionar uma nova linha ao arquivo. Não é permitido incluir 
 * identificador repetido. Neste caso, o metodo deve retornar false. Inclusão com
 * sucesso, retorno true.
 * 
 * A alteração deve substituir a linha atual por uma nova linha. A linha deve ser 
 * localizada por identificador que, quando não encontrado, enseja retorno false. 
 * Alteração com sucesso, retorno true.  
 *   
 * A exclusão deve apagar a linha atual do arquivo. A linha deve ser 
 * localizada por identificador que, quando não encontrado, enseja retorno false. 
 * Exclusão com sucesso, retorno true.
 * 
 * A busca deve localizar uma linha por identificador, materializar e retornar um 
 * objeto. Caso o identificador não seja encontrado no arquivo, retornar null.   
 */

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import java.io.*;
import java.util.*;

public class RepositorioEntidadeOperadora {

    private final String arquivo = "EntidadeOperadora.txt";

    // Create - Adiciona uma nova EntidadeOperadora
    public boolean create(EntidadeOperadora entidade) {
        if (read(entidade.getIdentificador()) != null) {
            return false; // Se a entidade já existe, não adiciona
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

    // Read - Lê e retorna uma EntidadeOperadora pelo identificador
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
        return null; // Se a entidade não for encontrada
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
        return false; // Se a entidade não foi encontrada
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
                    found = true; // Se encontrou a entidade, não a adiciona na nova lista
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            writeAll(entidades); // Reescreve o arquivo sem a entidade deletada
            return true;
        }
        return false; // Se a entidade não foi encontrada
    }

    // Método auxiliar para reescrever todas as entidades no arquivo
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
