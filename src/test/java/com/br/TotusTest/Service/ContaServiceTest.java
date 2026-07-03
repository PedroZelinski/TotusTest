package com.br.TotusTest.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.TotusTest.DTOs.ContaDTO;
import com.br.TotusTest.DTOs.Util.SituacaoConta;
import com.br.TotusTest.Repositories.ContaRepository;
import com.br.TotusTest.Services.ContaService;
import com.br.TotusTest.Services.FornecedorService;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @Mock
    private FornecedorService fornecedorService;

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;
    
    
    @Test
    void naoDeveCriarContaComFornecedorNulo() {

        ContaDTO dto = new ContaDTO(
                1L,
                "Conta teste",
                new Date(),
                new Date(),
                150.0,
                "descricao teste",
                SituacaoConta.PENDENTE,
                null, 
                "Fornecedor X"
        );

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> contaService.salvar(dto)
        );

        assertEquals("Fornecedor é obrigatório.", ex.getMessage());
    }
    
}
