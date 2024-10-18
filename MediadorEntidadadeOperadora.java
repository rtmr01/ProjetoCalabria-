package br.com.cesarschool.poo.titulos.mediators;

/*
 * Deve ser um singleton.
 *
 * Deve ter um atributo repositorioEntidadeOperadora, do tipo RepositorioEntidadeOperadora, que deve
 * ser inicializado na sua declaração. Este atributo será usado exclusivamente
 * pela classe, não tendo, portanto, métodos set e get.
 *
 * Métodos:
 *
 * pivate String validar(EntidadeOperadora): deve validar os dados do objeto recebido nos seguintes termos:
 * identificador: deve ser maior que zero e menor que 100000 (1)
 * nome: deve ser preenchido, diferente de branco e de null (2). deve ter entre 5 e 60 caracteres (3).
 * data de validade: deve ser maior do que a data atual mais 180 dias (4).
 * valorUnitario: deve ser maior que zero (5).
 * O metodo validar deve retornar null se o objeto estiver válido, e uma mensagem pertinente (ver abaixo)
 * se algum valor de atributo estiver inválido.
 *
 * (1) - Identificador deve estar entre 100 e 1000000.
 * (2) - Nome deve ser preenchido.
 * (3) - Nome deve ter entre 10 e 100 caracteres.
 *
 * public String incluir(EntidadeOperadora entidade): deve chamar o metodo validar. Se ele
 * retornar null, deve incluir entidade no repositório. Retornos possíveis:
 * (1) null, se o retorno do validar for null e o retorno do incluir do
 * repositório for true.
 * (2) a mensagem retornada pelo validar, se o retorno deste for diferente
 * de null.
 * (3) A mensagem "Entidade já existente", se o retorno do validar for null
 * e o retorno do repositório for false.
 *
 * public String alterar(EntidadeOperadora entidade): deve chamar o metodo validar. Se ele
 * retornar null, deve alterar entidade no repositório. Retornos possíveis:
 * (1) null, se o retorno do validar for null e o retorno do alterar do
 * repositório for true.
 * (2) a mensagem retornada pelo validar, se o retorno deste for diferente
 * de null.
 * (3) A mensagem "Entidade inexistente", se o retorno do validar for null
 * e o retorno do repositório for false.
 *
 * public String excluir(int identificador): deve validar o identificador.
 * Se este for válido, deve chamar o excluir do repositório. Retornos possíveis:
 * (1) null, se o retorno do excluir do repositório for true.
 * (2) A mensagem "Entidade inexistente", se o retorno do repositório for false
 * ou se o identificador for inválido.
 *
 * public EntidadeOperadora buscar(int identificador): deve validar o identificador.
 * Se este for válido, deve chamar o buscar do repositório, retornando o
 * que ele retornar. Se o identificador for inválido, retornar null.
 */


import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioEntidadeOperadora;

public class MediadorEntidadadeOperadora {

    private final RepositorioEntidadeOperadora repositorioEntidadeOperadora = new RepositorioEntidadeOperadora();

    // Singleton
    private static MediadorEntidadadeOperadora instance;

    private MediadorEntidadadeOperadora() {}

    public static MediadorEntidadadeOperadora getInstance() {
        if (instance == null) {
            instance = new MediadorEntidadadeOperadora();
        }
        return instance;
    }

    // Validação
    private String validar(EntidadeOperadora entidade) {
        if (entidade.getIdentificador() <100 || entidade.getIdentificador() > 1000000) {
            return "Identificador deve ser entre 100 e 1000000.";
        }

        String nome = entidade.getNome();
        if (nome == null || nome.trim().isEmpty()) {
            return "Por favor, preencha o nome.";
        }

        if (nome.length() < 10 || nome.length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }


        if (entidade.getSaldoAcao() <= 0) {
            return "Saldo de operação deve ser maior que 0.";
        }

        return null;
    }

    //CRUD

    public String incluir(EntidadeOperadora entidade) {
        String validacao = validar(entidade);
        if (validacao == null) {
            boolean incluido = repositorioEntidadeOperadora.create(entidade);
            if (incluido) {
                return null;
            } else {
                return "Entidade já existente";
            }
        } else {
            return validacao;
        }
    }

    public String alterar(EntidadeOperadora entidade) {
        String validacao = validar(entidade);
        if (validacao == null) {
            boolean alterado = repositorioEntidadeOperadora.update(entidade);
            if (alterado) {
                return null;
            } else {
                return "Entidade inexistente";
            }
        } else {
            return validacao; // Retorna mensagem de validação
        }
    }

    public String excluir(long identificador) {
        if (identificador <= 99 || identificador > 999999) {
            return "Identificador deve estar entre 100 e 1000000.";
        }
        boolean excluido = repositorioEntidadeOperadora.delete(identificador);
        if (excluido) {
            return null;
        } else {
            return "Entidade não existente";
        }
    }

    public EntidadeOperadora buscar(long identificador) {
        if (identificador < 100 || identificador >= 1000000) {
            System.out.println("O identificador não está válido");
            return null;
        }
        return repositorioEntidadeOperadora.read(identificador);
    }
}

