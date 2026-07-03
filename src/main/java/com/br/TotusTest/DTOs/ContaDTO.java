package com.br.TotusTest.DTOs;

import java.util.Date;

import com.br.TotusTest.DTOs.Util.SituacaoConta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ContaDTO(
		
        Long id,
        
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        
        @NotNull(message = "Data de vencimento é obrigatória")
        Date dataVencimento,
        
        Date dataPagamento,
        
        @Positive(message = "Valor deve ser maior que zero")
        double valor,
        String descricao,
        
        @NotBlank(message = "Situação é obrigatória")
        SituacaoConta situacao,
        
        @NotNull(message = "Fornecedor é obrigatório")
        Long fornecedorId,
        String fornecedorNome
) {}
