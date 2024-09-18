package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends
        CustomBaseJpaRepository<Pedido, Long>,
        JpaSpecificationExecutor<Pedido> {

    @Query("from Pedido p join fetch p.endereco join fetch p.formaPagamento join fetch p.restaurante r " +
            "join fetch p.usuario left join fetch r.formasPagamento join fetch r.cozinha")
    List<Pedido> todosPedidos();

    @Query("from Pedido p join fetch p.usuario join fetch p.endereco join fetch p.formaPagamento join fetch " +
            "p.restaurante r left join fetch r.produtos join fetch p.itensPedido i join fetch i.produto " +
            "where p.codigo = :codigo")
    Optional<Pedido> porCodigo(@Param("codigo") String codigoPedido);

    @Query("select count(p) > 0 from Pedido p join p.restaurante rest left join rest.usuariosResponsaveis resp " +
            "where p.codigo = :codigoPedido and resp.id = :usuarioId")
    boolean isRestauranteDoPedido(String codigoPedido, Long usuarioId);
}