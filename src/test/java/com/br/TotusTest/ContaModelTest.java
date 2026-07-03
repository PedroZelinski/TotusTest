package com.br.TotusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.br.TotusTest.DTOs.Util.SituacaoConta;
import com.br.TotusTest.Model.ContaModel;

class ContaModelTest {

    @Test
    void naoDevePermitirSituacaoNula() {
        ContaModel conta = new ContaModel();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> conta.setSituacao(null)
        );

        assertEquals("Situação é obrigatória.", ex.getMessage());
    }
    
    @Test
    void devePermitirSituacaoValida() {
        ContaModel conta = new ContaModel();

        conta.setSituacao(SituacaoConta.PENDENTE);

        assertEquals(SituacaoConta.PENDENTE, conta.getSituacao());
    }
    
    @Test
    void naoDevePermitirPagoParaPendente() {
        ContaModel conta = new ContaModel();

        conta.setSituacao(SituacaoConta.PAGO);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> conta.setSituacao(SituacaoConta.PENDENTE)
        );

        assertEquals("Uma conta paga não pode voltar para pendente.", ex.getMessage());
    }
    
    @Test
    void devePermitirPagoParaCancelado() {
        ContaModel conta = new ContaModel();

        conta.setSituacao(SituacaoConta.PAGO);
        conta.setSituacao(SituacaoConta.CANCELADO);

        assertEquals(SituacaoConta.CANCELADO, conta.getSituacao());
    }
}