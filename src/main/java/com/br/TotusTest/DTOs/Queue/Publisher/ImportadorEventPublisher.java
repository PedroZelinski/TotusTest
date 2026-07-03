package com.br.TotusTest.DTOs.Queue.Publisher;

import com.br.TotusTest.DTOs.CSVDTO;

public interface ImportadorEventPublisher {
    void publicar(CSVDTO dto);
}
