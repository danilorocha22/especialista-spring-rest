package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends CustomBaseJpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

    @Query("from Pedido p join fetch p.endereco join fetch p.formaPagamento join fetch p.restaurante r " +
            "join fetch p.usuario join fetch p.itensPedido ip join fetch r.cozinha join fetch ip.produto " +
            "where p.id = :id")
    Optional<Pedido> porId(Long id);

    @Query("from Pedido p join fetch p.endereco join fetch p.formaPagamento join fetch p.restaurante r " +
            "join fetch p.usuario left join fetch r.formasPagamento join fetch r.cozinha")
    List<Pedido> todosPedidos();

    @Query("from Pedido p where p.codigo = :codigo")
    Optional<Pedido> porCodigo(@Param("codigo") String codigoPedido);
}