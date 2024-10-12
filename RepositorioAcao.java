package br.com.cesarschool.poo.titulos.repositorios;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/*
 * Deve gravar em e ler de um arquivo texto chamado Acao.txt os dados dos objetos do tipo
 * Acao. Seguem abaixo exemplos de linhas (identificador, nome, dataValidade, valorUnitario)
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



public class RepositorioAcao {
	private static final String FILE_NAME = "Acao.txt";

	// incluir linhas no arquivo
	public boolean incluir(Acao acao) {
		if (buscar(acao.getIdentificador()) != null) {
			return false; // Identificador j� existe
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
			writer.write(acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataDeValidade() + ";" + acao.getValorUnitario());
			writer.newLine();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	//  alterar uma linha no arquivo
	public boolean alterar(Acao acao) {
		List<String> linhas = new ArrayList<>();
		boolean encontrado = false;

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;

			while ((linha = reader.readLine()) != null) {
				String[] campos = linha.split(";");
				int id = Integer.parseInt(campos[0]);

				if (id == acao.getIdentificador()) {
					linhas.add(acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataDeValidade() + ";" + acao.getValorUnitario());
					encontrado = true;
				} else {
					linhas.add(linha);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		if (!encontrado) {
			return false;
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
			for (String linha : linhas) {
				writer.write(linha);
				writer.newLine();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// excluir uma linha no arquivo
	public boolean excluir(int identificador) {
		List<String> linhas = new ArrayList<>();
		boolean encontrado = false;

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;

			while ((linha = reader.readLine()) != null) {
				String[] campos = linha.split(";");
				int id = Integer.parseInt(campos[0]);

				if (id != identificador) {
					linhas.add(linha);
				} else {
					encontrado = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		if (!encontrado) {
			return false;
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
			for (String linha : linhas) {
				writer.write(linha);
				writer.newLine();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// buscar uma linha por identificador e retornar um objeto Acao
	public Acao buscar(int identificador) {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;

			while ((linha = reader.readLine()) != null) {
				String[] campos = linha.split(";");
				int id = Integer.parseInt(campos[0]);

				if (id == identificador) {
					String nome = campos[1];
					LocalDate dataDeValidade = LocalDate.parse(campos[2]);
					double valorUnitario = Double.parseDouble(campos[3]);
					return new Acao(id, nome, dataDeValidade, valorUnitario);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
