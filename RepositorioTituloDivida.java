package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * Deve gravar em e ler de um arquivo texto chamado TituloDivida.txt os dados dos objetos do tipo
 * TituloDivida. Seguem abaixo exemplos de linhas (identificador, nome, dataValidade, taxaJuros).
 *
    1;BRASIL;2024-12-12;10.5
    2;EUA;2026-01-01;1.5
    3;FRANCA;2027-11-11;2.5 
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

public class RepositorioTituloDivida {

	private static final String FILE_NAME = "TituloDivida.txt";

	// incluir um novo título de dívida no arquivo NAO É PERMITIDIO
	public boolean incluir(TituloDivida tituloDivida) {
		if (buscar(tituloDivida.getIdentificador()) != null) {
			return false; // Identificador já existe
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
			writer.write(formatTituloDivida(tituloDivida) + "\n");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// MEtodo para alterar um título de dívida existente
	public boolean alterar(TituloDivida tituloDivida) {
		List<String> linhas = new ArrayList<>();
		boolean encontrado = false;

		// Lê todas as linhas do arquivo e substitui a linha com o identificador correspondente
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] dados = linha.split(";");
				int id = Integer.parseInt(dados[0]);

				if (id == tituloDivida.getIdentificador()) {
					linhas.add(formatTituloDivida(tituloDivida)); // Substitui a linha
					encontrado = true;
				} else {
					linhas.add(linha); // Mantém as outras linhas
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// Se o identificador foi encontrado, reescreve o arquivo com as alterações
		if (encontrado) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
				for (String linha : linhas) {
					writer.write(linha + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		return false; // Identificador não encontrado
	}

	// Metodo para excluir um título de dívida do arquivo
	public boolean excluir(int identificador) {
		List<String> linhas = new ArrayList<>();
		boolean encontrado = false;

		// Lê todas as linhas e remove a linha com o identificador correspondente
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] dados = linha.split(";");
				int id = Integer.parseInt(dados[0]);

				if (id == identificador) {
					encontrado = true; // Identificador encontrado, não adiciona a linha
				} else {
					linhas.add(linha); // Mantém as outras linhas
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// Reescreve o arquivo com as linhas restantes se o identificador foi encontrado
		if (encontrado) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
				for (String linha : linhas) {
					writer.write(linha + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		return false; // Identificador não encontrado
	}

	// Metodo para buscar um título de dívida por identificador
	public TituloDivida buscar(int identificador) {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] dados = linha.split(";");
				int id = Integer.parseInt(dados[0]);

				if (id == identificador) {
					// Retorna um objeto TituloDivida a partir dos dados lidos
					return new TituloDivida(id, dados[1], LocalDate.parse(dados[2]), Double.parseDouble(dados[3]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null; // Identificador não encontrado
	}

	// Pra ficar mais facil de manipular os dados que sao pegos, eu fiz um metodo pra isso (gpt sugeriu kkkkkkkkkk)
	private String formatTituloDivida(TituloDivida tituloDivida) {
		return tituloDivida.getIdentificador() + ";" + tituloDivida.getNome() + ";"
				+ tituloDivida.getDataDeValidade() + ";" + tituloDivida.getTaxaJuros();
	}
}
