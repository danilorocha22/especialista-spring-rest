package com.dan.esr.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.dan.esr.domain.services.StorageAlbumService;
import com.dan.esr.infrastructure.services.storage.LocalAlbumStorageService;
import com.dan.esr.infrastructure.services.storage.S3AlbumStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.dan.esr.core.storage.StorageProperties.TipoStorage;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {
    private final StorageProperties storageProperties;

    @Bean
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(
                storageProperties.getIdChaveAcessoS3(),
                storageProperties.getChaveAcessoSecretaS3());

        return AmazonS3Client.builder()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getRegiaoS3())
                .build();
    }

    @Bean
    public StorageAlbumService storageAlbumService() {
        if (TipoStorage.S3.equals(storageProperties.getTipo())) {
            return new S3AlbumStorageService();
        } else {
            return new LocalAlbumStorageService();
        }
    }
}