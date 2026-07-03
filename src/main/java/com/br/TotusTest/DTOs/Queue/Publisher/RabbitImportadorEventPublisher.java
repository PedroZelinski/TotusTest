package com.br.TotusTest.DTOs.Queue.Publisher;

import com.br.TotusTest.Config.RabbitMQConfig;
import com.br.TotusTest.DTOs.CSVDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitImportadorEventPublisher implements ImportadorEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publicar(CSVDTO dto) {

        log.info(
                "Publicando evento de importação. Protocolo: {}",
                dto.protocolo()
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                dto
        );

        log.info(
                "Evento publicado com sucesso. Exchange: {} | RoutingKey: {} | Protocolo: {}",
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                dto.protocolo()
        );
    }
}