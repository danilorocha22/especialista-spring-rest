package com.dan.esr.infrastructure.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dan.esr.core.storage.StorageProperties;
import com.dan.esr.domain.services.StorageAlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3StorageAlbumService implements StorageAlbumService {
    private final AmazonS3 amazonS3;
    private final StorageProperties storageProperties;

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
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            this.amazonS3.putObject(putObjectRequest);
        } catch (Exception ex) {
            throw new StorageException("Não foi possível enviar o arquivo para Amazon S3", ex);
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
            throw new StorageException("Não foi possível excluir o arquivo na Amazon S3", ex);
        }
    }

    @Override
    public InputStream baixar(String nomeArquivo) {
        return null;
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return "%s/%s".formatted(this.storageProperties.getDiretorioFotosS3(), nomeArquivo);
    }
}