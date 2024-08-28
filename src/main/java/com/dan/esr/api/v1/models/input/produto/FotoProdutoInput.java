package com.dan.esr.api.v1.models.input.produto;

import com.dan.esr.core.validation.ArquivoTamanho;
import com.dan.esr.core.validation.TipoConteudoArquivo;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.MediaType.*;

@Getter
@Setter
public class FotoProdutoInput {
    @NotNull
    @ArquivoTamanho(max = "500KB")
    @ApiModelProperty(hidden = true)
    @TipoConteudoArquivo(tiposPermitidos = {IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE})
    private MultipartFile arquivo;

    @NotBlank
    @ApiModelProperty(value = "Descrição da foto do produto", required = true)
    private String descricao;

    public String getNomeArquivo() {
        return this.arquivo.getOriginalFilename();
    }

    public String getContentType() {
        return this.arquivo.getContentType();
    }

    public Long getTamanho() {
        return this.arquivo.getSize();
    }

    public InputStream getInputStream() throws IOException {
        return this.arquivo.getInputStream();
    }
}