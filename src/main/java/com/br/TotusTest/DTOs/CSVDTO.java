package com.br.TotusTest.DTOs;

import java.time.LocalDateTime;

public record CSVDTO(
	    String protocolo,
	    String nomeArquivo,
	    String status,
	    LocalDateTime criadoEm
	) {}