package com.dan.esr.infrastructure.services.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dan.esr.core.storage.StorageProperties;
import com.dan.esr.core.helper.LoggerHelper;
import com.dan.esr.domain.services.StorageAlbumService;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

import static com.amazonaws.services.s3.model.CannedAccessControlList.PublicRead;

//@Service
public class S3AlbumStorageService implements StorageAlbumService {
    private static final LoggerHelper logger = new LoggerHelper(LocalAlbumStorageService.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());

            var putObjectRequest = new PutObjectRequest(
                    this.storageProperties.getBucketS3(),
                    caminhoArquivo,
                    novaFoto.getInputStream(),
                    objectMetadata
            ).withCannedAcl(PublicRead);

            this.amazonS3.putObject(putObjectRequest);

        } catch (Exception ex) {
            logger.error("armazenar(NovaFoto novaFoto) -> Erro: {}", ex.getLocalizedMessage(), ex);
            throw new StorageException("Não foi possível fazer o upload do arquivo da foto do produto.", ex);
        }
    }

    @Override
    public void excluir(String nomeArquivo) {
        try {
            var delObjectRequest = new DeleteObjectRequest(
                    this.storageProperties.getBucketS3(),
                    getCaminhoArquivo(nomeArquivo)
            );

            this.amazonS3.deleteObject(delObjectRequest);

        } catch (Exception ex) {
            logger.error(" excluir(String nomeArquivo) -> Erro: {}", ex.getLocalizedMessage(), ex);
            throw new StorageException("Não foi possível excluir o arquivo da foto do produto.", ex);
        }
    }

    @Override
    public FotoRecuperada baixar(String nomeArquivo) {
        try {
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
            URL url = this.amazonS3.getUrl(this.storageProperties.getBucketS3(), caminhoArquivo);

            return FotoRecuperada.builder()
                    .url(url.toString())
                    .build();

        } catch (Exception ex) {
            logger.error(" baixar(String nomeArquivo) -> Erro: {}", ex.getLocalizedMessage(), ex);
            throw new StorageException("Não foi possível baixar o arquivo da foto do produto.", ex);
        }
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return "%s/%s".formatted(this.storageProperties.getDiretorioFotosS3(), nomeArquivo);
    }
}