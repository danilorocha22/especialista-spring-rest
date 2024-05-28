package com.dan.esr.core.util;

public final class MensagensUtil {
    /*####################   GERAL   ####################*/
    public static final String MSG_SE_PROBLEMA_PERSISTIR = "Se o problema persistir, entre em contato com o " +
            "suporte técnico.";

    public static final String MSG_ERRO_GENERICO_SERVIDOR = "Ops! Parece que algo deu errado no servidor. " +
            MSG_SE_PROBLEMA_PERSISTIR;

    public static final String MSG_ERRO_GENERICO_CLIENTE = "Ops! Parece que algo deu errado do seu lado. Por favor, " +
            "verifique os dados que você forneceu e tente novamente. " + MSG_SE_PROBLEMA_PERSISTIR;

    public static final String MSG_PROPRIEDADE_INVALIDA = "Um ou mais campos estão inválidos e devem ser informados " +
            "corretamente.";

    public static final String MSG_ERRO_BANCO_DE_DADOS = "Ocorreu um erro durante a operação com o banco de dados. " +
            MSG_SE_PROBLEMA_PERSISTIR;

    public static final String MSG_PROPRIEDADE_NAO_PODE_SER_NULA = "Não é permitido propriedade nula";

    public static final String MSG_PROPRIEDADE_ILEGAL = "Não é permitido informar o(a) %s.";

    /*####################   RESTAURANTE   ####################*/
    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO_COM_NOME = "Nenhum restaurante encontrado com nome: %s.";

    public static final String MSG_RESTAURANTE_EM_USO = "Restaurante com ID %s não pode ser removido" +
            " pois possui relacionamento com outra entidade.";

    public static final String MSG_RESTAURANTE_NAO_SALVO = "Não foi possível realizar o cadastro do restaurante: %s.";

    public static final String MSG_RESTAURANTE_NAO_ATUALIZADO = "Não foi possível atualizar o cadastro do restaurante: %s.";

    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Nenhum restaurante encontrado.";

    /*####################   COZINHA   ####################*/
    public static final String MSG_COZINHA_NAO_ENCONTRADA = "Nenhuma cozinha não encontrada.";

    public static final String MSG_COZINHA_NAO_ENCONTRADA_COM_NOME = "Cozinha não encontrada com nome: %s.";

    public static final String MSG_COZINHA_EM_USO = "Cozinha com ID %s não pode ser removida" +
            " pois possui relacionamento com outra entidade.";

    public static final String MSG_COZINHA_NAO_SALVA = "Não foi possível realizar o cadastro da cozinha: %s.";

    public static final String MSG_COZINHA_NAO_ATUALIZADA = "Não foi possível atualizar o cadastro da cozinha: %s.";

    /*####################   CIDADE   ####################*/
    public static final String MSG_CIDADE_EM_USO = "Cidade com ID %s não pode ser removida" +
            " pois possui relacionamento com outra entidade.";

    public static final String MSG_CIDADE_NAO_ENCONTRADA = "Nenhuma cidade encontrada.";

    public static final String MSG_CIDADE_NAO_SALVA = "Não foi possível realizar o cadastro da cidade: %s.";

    public static final String MSG_CIDADE_NAO_ATUALIZADA = "Não foi possível atualizar o cadastro da cidade: %s.";

    /*####################   ESTADO   ####################*/
    public static final String MSG_ESTADO_EM_USO = "Estado com ID %s, está em uso com " +
            "e não pode ser excluído";

    private MensagensUtil() {
    }

    public static String formatarNomeEntidadeInput(String nome) {
        return (nome.endsWith("Input")) ? nome.replace("Input", "") : nome;
    }
}