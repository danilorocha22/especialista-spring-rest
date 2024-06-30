package com.dan.esr.core.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public final class PaginacaoCamposHelper {
    private PaginacaoCamposHelper() {
    }

    public static Pageable novaPaginacaoCamposConfigurados(Pageable pageable, Map<String, String> campos) {
        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                configurarCamposParaOrdenacao(pageable.getSort(), campos)
        );
    }

    private static Sort configurarCamposParaOrdenacao(Sort sort, Map<String, String> campos) {
        List<Sort.Order> orders = sort.stream()
                .filter(order -> campos.containsKey(order.getProperty()))
                .map(order -> new Sort.Order(order.getDirection(), campos.get(order.getProperty())))
                .toList();

        return Sort.by(orders);
    }
}