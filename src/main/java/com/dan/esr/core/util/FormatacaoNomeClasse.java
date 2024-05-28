package com.dan.esr.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatacaoNomeClasse {
    // Dicionário de termos especiais e preposições
    private static final Map<String, String> TERMOS = new HashMap<>();
    static {
        TERMOS.put("FormaPagamento", "Forma de pagamento");
        TERMOS.put("Usuario", "Usuário");
        /*TERMOS.put("Grupo", "Grupo");
        TERMOS.put("Produto", "Produto");*/
    }

    public static String formatarNomeClasse(String nome) {
        Objects.requireNonNull(nome, "Nome da classe não pode ser nulo");

        if (TERMOS.containsKey(nome)) {
            return TERMOS.get(nome);
        }

        // Converte CamelCase para palavras separadas por espaços
        return converterCamelCaseParaPalavraNatural(nome);
    }

    private static String converterCamelCaseParaPalavraNatural(String str) {
        Pattern pattern = Pattern.compile("([a-z])([A-Z])");
        Matcher matcher = pattern.matcher(str);
        StringBuilder resultado = new StringBuilder();

        while (matcher.find()) {
            matcher.appendReplacement(resultado, matcher.group(1) + " " + matcher.group(2));
        }
        matcher.appendTail(resultado);

        return resultado.toString();
    }
}