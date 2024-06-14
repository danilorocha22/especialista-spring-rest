package com.dan.esr.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@NoArgsConstructor
@ConfigurationProperties("danfood.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();
    private TipoStorage tipo = TipoStorage.LOCAL;

    public Path getDiretorioFotosLocal() {
        return this.local.diretorioFotos;
    }

    public String getIdChaveAcessoS3() {
        return this.s3.idChaveAcesso;
    }

    public String getChaveAcessoSecretaS3() {
        return this.s3.chaveAcessoSecreta;
    }

    public String getBucketS3() {
        return this.s3.bucket;
    }

    public Regions getRegiaoS3() {
        return this.s3.regiao;
    }

    public String getDiretorioFotosS3() {
        return this.s3.diretorioFotos;
    }

    @Getter
    @Setter
    public static class Local {
        private Path diretorioFotos;
    }

    @Getter
    @Setter
    public static class S3 {
        private String idChaveAcesso;
        private String chaveAcessoSecreta;
        private String bucket;
        private Regions regiao;
        private String diretorioFotos;
    }

    public enum TipoStorage {
        LOCAL, S3
    }
}