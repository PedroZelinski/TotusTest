package com.br.TotusTest.DTOs;

import java.time.LocalDate;

public record ContaFiltroDTO(
        LocalDate dataVencimentoInicio,
        LocalDate dataVencimentoFim,
        String descricao
) {
}