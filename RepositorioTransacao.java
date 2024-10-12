package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/*
 * Deve gravar em e ler de um arquivo texto chamado Transacao.txt os dados dos objetos do tipo
 * Transacao. Seguem abaixo exemplos de linhas 
 * De entidadeCredito: identificador, nome, autorizadoAcao, saldoAcao, saldoTituloDivida.
 * De entidadeDebito: identificador, nome, autorizadoAcao, saldoAcao, saldoTituloDivida.
 * De acao: identificador, nome, dataValidade, valorUnitario OU null
 * De tituloDivida: identificador, nome, dataValidade, taxaJuros OU null. 
 * valorOperacao, dataHoraOperacao
 * 
 *   002192;BCB;true;0.00;1890220034.0;001112;BOFA;true;12900000210.00;3564234127.0;1;PETROBRAS;2024-12-12;30.33;null;100000.0;2024-01-01 12:22:21 
 *   002192;BCB;false;0.00;1890220034.0;001112;BOFA;true;12900000210.00;3564234127.0;null;3;FRANCA;2027-11-11;2.5;100000.0;2024-01-01 12:22:21
 *
 * A inclus�o deve adicionar uma nova linha ao arquivo. 
 * 
 * A busca deve retornar um array de transa��es cuja entidadeCredito tenha identificador igual ao
 * recebido como par�metro.  
 */


public class RepositorioTransacao {

	private static final String FILE_NAME = "Transacao.txt";

	// Metodo para incluir uma nova transa��o no arquivo
	public void incluir(Transacao transacao) {



		//ginho colocou um verificador desse, analisar depois
		//if (transacao.getEntidadeCredito().getIdentificador() == transacao.getEntidadeDebito().getIdentificador()) {
		//	System.out.println("A entidade credora n�o pode ser a mesma que a entidade devedora.");
		//	return; // N�o permite transa��es entre a mesma entidade
	//	}





		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
			// Escreve os dados da entidade credora
			writer.write(formatEntidade(transacao.getEntidadeCredito()) + ";");
			// Escreve os dados da entidade devedora
			writer.write(formatEntidade(transacao.getEntidadeDebito()) + ";");
			// Escreve os dados da a��o (ou null)
			writer.write(formatAcao(transacao.getAcao()) + ";");
			// Escreve os dados do t�tulo de d�vida (ou null)
			writer.write(formatTituloDivida(transacao.getTituloDivida()) + ";");
			// Escreve o valor da opera��o e a data/hora da opera��o
			writer.write(transacao.getValorOperacao() + ";" + transacao.getDataHoraOperacao() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Metodo para buscar transa��es por identificador da entidade credora
	public Transacao[] buscarPorEntidadeCredora(int identificadorEntidadeCredito) {
		List<Transacao> transacoesEncontradas = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] dados = linha.split(";");
				int idCredito = Integer.parseInt(dados[0]); // identificador da entidade credora
				if (idCredito == identificadorEntidadeCredito) {
					transacoesEncontradas.add(parseTransacao(dados));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return transacoesEncontradas.toArray(new Transacao[0]);
	}

	// Metodo auxiliar para formatar os dados de uma entidade
	private String formatEntidade(EntidadeOperadora entidade) {
		return entidade.getIdentificador() + ";" + entidade.getNome() + ";" + entidade.getAutorizadoAcao() + ";"
				+ entidade.getSaldoAcao() + ";" + entidade.getSaldoTituloDivida();
	}

	// Metodo auxiliar para formatar os dados de uma a��o
	private String formatAcao(Acao acao) {
		if (acao == null) {
			return "null";
		}
		return acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataDeValidade() + ";" + acao.getValorUnitario();
	}

	//  auxiliar para formatar os dados de um t�tulo de d�vida
	private String formatTituloDivida(TituloDivida tituloDivida) {
		if (tituloDivida == null) {
			return "null";
		}
		return tituloDivida.getIdentificador() + ";" + tituloDivida.getNome() + ";" + tituloDivida.getDataDeValidade() + ";" + tituloDivida.getTaxaJuros();
	}




	// serve para criar uma Transacao a partir dos dados lidos
	private Transacao parseTransacao(String[] dados) {
		// Cria a entidade credora
		EntidadeOperadora entidadeCredito = new EntidadeOperadora(
				Long.parseLong(dados[0]), dados[1], Double.parseDouble(dados[2]), Double.parseDouble(dados[3]), Double.parseDouble(dados[4]));

		// Cria a entidade devedora
		EntidadeOperadora entidadeDebito = new EntidadeOperadora(
				Long.parseLong(dados[5]), dados[6], Double.parseDouble(dados[7]), Double.parseDouble(dados[8]), Double.parseDouble(dados[9]));

		// Cria a a��o (ou null)
		Acao acao = dados[10].equals("null") ? null : new Acao(
				Integer.parseInt(dados[10]), dados[11], LocalDate.parse(dados[12]), Double.parseDouble(dados[13]));

		// Cria o t�tulo de d�vida (ou null)
		TituloDivida tituloDivida = dados[14].equals("null") ? null : new TituloDivida(
				Integer.parseInt(dados[14]), dados[15], LocalDate.parse(dados[16]), Double.parseDouble(dados[17]));

		// Recupera o valor da opera��o e a data/hora da opera��o
		double valorOperacao = Double.parseDouble(dados[18]);
		LocalDateTime dataHoraOperacao = LocalDateTime.parse(dados[19]);

		// Retorna a nova transa��o
		return new Transacao(entidadeCredito, entidadeDebito, acao, tituloDivida, valorOperacao, dataHoraOperacao);
	}
}
