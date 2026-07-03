package com.br.TotusTest.DTOs.Queue.Consumer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.br.TotusTest.Config.RabbitMQConfig;
import com.br.TotusTest.DTOs.CSVDTO;
import com.br.TotusTest.DTOs.Util.StatusImportacao;
import com.br.TotusTest.Model.CSVModel;
import com.br.TotusTest.Repositories.CSVRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImportadorCSVConsumer {

    private final CSVRepository repository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumir(CSVDTO dto) {

        log.info("Mensagem recebida. Protocolo: {}", dto.protocolo());

        CSVModel entity = repository.findByProtocolo(dto.protocolo())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Importação não encontrada. Protocolo: " + dto.protocolo()));

        try {

            entity.setStatus(StatusImportacao.PROCESSANDO);
            entity.setAtualizadoEm(LocalDateTime.now());

            repository.save(entity);

            Path arquivo = Path.of(entity.getCaminhoArquivo());

            if (!Files.exists(arquivo)) {
                throw new IllegalStateException(
                        "Arquivo não encontrado: " + arquivo.toAbsolutePath());
            }

            long tamanhoBytes = Files.size(arquivo);

            double tamanhoMB = tamanhoBytes / (1024d * 1024d);

            log.info(
            	    "Arquivo '{}' possui {} bytes ({} MB)",
            	    entity.getNomeArquivo(),
            	    tamanhoBytes,
            	    String.format("%.2f", tamanhoMB)
            	);;

            entity.setStatus(StatusImportacao.CONCLUIDO);
            entity.setAtualizadoEm(LocalDateTime.now());

            repository.save(entity);

            log.info(
                    "Importação concluída. Protocolo: {}",
                    dto.protocolo());

        } catch (Exception ex) {

            log.error(
                    "Erro ao processar importação. Protocolo: {}",
                    dto.protocolo(),
                    ex
            );

            entity.setStatus(StatusImportacao.ERRO);
            entity.setMensagemErro(ex.getMessage());
            entity.setAtualizadoEm(LocalDateTime.now());

            repository.save(entity);
        }
    }
}