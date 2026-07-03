package com.br.TotusTest.Model;

import java.util.Date;

import com.br.TotusTest.DTOs.Util.SituacaoConta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    
    private Date dataVencimento;

    private Date dataPagamento;
    
    private double valor;
    
    private String descricao;
  
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoConta situacao;
    
    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private FornecedorModel fornecedor;
    
    public void setSituacao(SituacaoConta novaSituacao) {

        if (novaSituacao == null) {
            throw new IllegalArgumentException("Situação é obrigatória.");
        }

        if (this.situacao == SituacaoConta.PAGO
                && novaSituacao == SituacaoConta.PENDENTE) {

            throw new IllegalStateException(
                    "Uma conta paga não pode voltar para pendente."
            );
        }

        this.situacao = novaSituacao;
    }
    
    public void setValor(double valor) {

        if (valor < 0) {
            throw new IllegalArgumentException(
                    "O valor da conta não pode ser negativo."
            );
        }

        this.valor = valor;
    }
}

