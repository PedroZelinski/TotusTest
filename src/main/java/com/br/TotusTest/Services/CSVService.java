package com.br.TotusTest.Services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.br.TotusTest.DTOs.CSVDTO;
import com.br.TotusTest.DTOs.Queue.Publisher.ImportadorEventPublisher;
import com.br.TotusTest.DTOs.Util.StatusImportacao;
import com.br.TotusTest.Model.CSVModel;
import com.br.TotusTest.Repositories.CSVRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CSVService {

    private final CSVRepository repository;
    private final FileStorageService fileStorageService;
    private final ImportadorEventPublisher publisher;

    @Transactional
    public CSVDTO receberArquivo(MultipartFile file) {

    	if (file.isEmpty()) throw new IllegalArgumentException("Arquivo vazio");
    	if (!file.getOriginalFilename().endsWith(".csv")) throw new IllegalArgumentException("Formato inválido");
    	
        log.info("Recebendo arquivo '{}'", file.getOriginalFilename());

        String protocolo = UUID.randomUUID().toString();

        String caminho = fileStorageService.salvar(file, protocolo);

        CSVModel entity = CSVModel.criar(
                protocolo,
                file.getOriginalFilename(),
                caminho
        );

        repository.save(entity);

        log.info("Importação registrada. Protocolo: {}", protocolo);

        CSVDTO dto = new CSVDTO(
                entity.getProtocolo(),
                entity.getNomeArquivo(),
                StatusImportacao.RECEBIDO.name(),
                entity.getCriadoEm()
        );

        publisher.publicar(dto);

        log.info("Evento enviado ao RabbitMQ. Protocolo: {}", protocolo);

        return dto;
    }
}