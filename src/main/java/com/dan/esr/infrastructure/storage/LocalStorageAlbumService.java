package com.dan.esr.infrastructure.storage;

import com.dan.esr.core.storage.StorageProperties;
import com.dan.esr.core.util.LoggerHelper;
import com.dan.esr.domain.services.StorageAlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

//@Service
@RequiredArgsConstructor
public class LocalStorageAlbumService implements StorageAlbumService {
    private static final LoggerHelper logger = new LoggerHelper(LocalStorageAlbumService.class);
    private final StorageProperties storageProperties;

    /*@Value("${danfood.storage.local.diretorio-fotos}")
    private Path diretorioFotos;*/

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path caminhoArquivo = getArquivoPath(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(caminhoArquivo));

        } catch (Exception ex) {
            logger.error("armazenar(NovaFoto novaFoto) -> Erro: {}", ex.getLocalizedMessage(), ex);

            throw new StorageException("Ocorreu um erro ao tentar gravar arquivo %s no disco.".
                    formatted(novaFoto.getNomeArquivo()), ex);
        }
    }

    @Override
    public void excluir(String nomeArquivo) {
        try {
            Objects.requireNonNull(nomeArquivo, "Nome do arquivo da foto é obrigatório");
            Path caminhoArquivo = getArquivoPath(nomeArquivo);
            Files.deleteIfExists(caminhoArquivo);
        } catch (Exception ex) {
            logger.error("excluir(String nomeArquivo) -> Erro: {}", ex.getLocalizedMessage(), ex);

            throw new StorageException("Ocorreu um erro ao tentar excluir o arquivo %s do disco."
                    .formatted(nomeArquivo), ex);
        }
    }

    @Override
    public InputStream baixar(String nomeArquivo) {
        try {
            Objects.requireNonNull(nomeArquivo, "Nome do arquivo da foto é obrigatório");
            Path caminhoArquivo = getArquivoPath(nomeArquivo);
            return Files.newInputStream(caminhoArquivo);
        } catch (Exception ex) {
            logger.error("baixar(String nomeArquivo) -> Erro: {}", ex.getLocalizedMessage(), ex);

            throw new StorageException("Ocorreu um erro ao tentar baixar o arquivo %s."
                    .formatted(nomeArquivo), ex);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return this.storageProperties.getDiretorioFotosLocal().resolve(Path.of(nomeArquivo));
    }
}