package com.br.TotusTest.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {

    private static final String BASE_DIR = "C:/Users/pedro/importacoes";

    public String salvar(MultipartFile file, String protocolo) {

        long inicio = System.currentTimeMillis();

        log.info("Iniciando armazenamento do arquivo. Protocolo: {}", protocolo);

        validarArquivo(file);

        try {

            Path diretorio = Paths.get(
                    BASE_DIR,
                    LocalDate.now().toString()
            );

            Files.createDirectories(diretorio);

            log.debug("Diretório de armazenamento: {}", diretorio);

            String extensao = extrairExtensao(file.getOriginalFilename());

            String nomeArquivo = protocolo + "_" + UUID.randomUUID() + extensao;

            Path destino = diretorio.resolve(nomeArquivo);

            log.info("Salvando arquivo '{}' em '{}'",
                    file.getOriginalFilename(),
                    destino.toAbsolutePath());

            Files.copy(
                    file.getInputStream(),
                    destino,
                    StandardCopyOption.REPLACE_EXISTING
            );

            long tempo = System.currentTimeMillis() - inicio;

            log.info(
                    "Arquivo salvo com sucesso. Protocolo: {} | Caminho: {} | Tempo: {} ms",
                    protocolo,
                    destino.toAbsolutePath(),
                    tempo
            );

            return destino.toAbsolutePath().toString();

        } catch (IOException e) {

            log.error(
                    "Erro ao salvar arquivo. Protocolo: {} | Arquivo: {}",
                    protocolo,
                    file.getOriginalFilename(),
                    e
            );

            throw new RuntimeException("Erro ao salvar arquivo.", e);
        }
    }

    private void validarArquivo(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            log.warn("Tentativa de envio de arquivo vazio.");
            throw new IllegalArgumentException("Arquivo vazio.");
        }

        if (file.getOriginalFilename() == null ||
                !file.getOriginalFilename().toLowerCase().endsWith(".csv")) {

            log.warn("Arquivo inválido recebido: {}", file.getOriginalFilename());

            throw new IllegalArgumentException(
                    "Somente arquivos CSV são permitidos."
            );
        }
    }

    private String extrairExtensao(String nomeArquivo) {

        int indice = nomeArquivo.lastIndexOf('.');

        return indice > 0
                ? nomeArquivo.substring(indice)
                : "";
    }
}