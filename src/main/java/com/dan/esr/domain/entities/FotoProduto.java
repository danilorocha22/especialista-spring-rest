package com.dan.esr.domain.entities;

import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static org.springframework.http.MediaType.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "album", schema = "dan_food")
public class FotoProduto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "produto_id")
    private Long id;

    private String nomeArquivo;
    private String contentType;
    private Long tamanho;
    private String descricao;

    @MapsId // configura para que a propriedade produto seja mapeada por produto_id
    @OneToOne(fetch = FetchType.LAZY)
    private Produto produto;

    public Restaurante getRestaurante() {
        return this.produto.getRestaurante();
    }

    public void validarMediaType(String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        MediaType mediaTypeFoto = parseMediaType(this.getContentType());
        List<MediaType> mediaTypesRequeridas = parseMediaTypes(acceptHeader);
        if (!isMediasTypeCompativeis(mediaTypeFoto, mediaTypesRequeridas)) {
            throw new HttpMediaTypeNotAcceptableException(List.of(mediaTypeFoto));
        }
    }

    private boolean isMediasTypeCompativeis(MediaType mediaTypeFoto, List<MediaType> mediaTypesRequeridas) {
        return mediaTypesRequeridas.stream()
                .anyMatch(mediaType -> mediaType.isCompatibleWith(mediaTypeFoto));
    }
}