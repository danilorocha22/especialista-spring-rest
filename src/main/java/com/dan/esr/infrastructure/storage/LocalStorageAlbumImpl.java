package com.dan.esr.infrastructure.storage;

import com.dan.esr.core.util.LoggerHelper;
import com.dan.esr.domain.services.LocalStorageAlbumService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalStorageAlbumImpl implements LocalStorageAlbumService {
    private static final LoggerHelper logger = new LoggerHelper(LocalStorageAlbumImpl.class);

    @Value("${danfood.storage.local.diretorio-fotos}")
    private Path diretorioFotos;

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
            Path caminhoArquivo = getArquivoPath(nomeArquivo);
            Files.deleteIfExists(caminhoArquivo);
        } catch (Exception ex) {
            logger.error("excluir(String nomeArquivo) -> Erro: {}", ex.getLocalizedMessage(), ex);

            throw new StorageException("Ocorreu um erro ao tentar excluir o arquivo %s do disco."
                    .formatted(nomeArquivo), ex);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return diretorioFotos.resolve(Path.of(nomeArquivo));
    }
}