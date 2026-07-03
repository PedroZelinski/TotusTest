package com.br.TotusTest.Mappers;

import java.util.List;

import com.br.TotusTest.DTOs.ContaDTO;
import com.br.TotusTest.Model.ContaModel;
import com.br.TotusTest.Model.FornecedorModel;

public class ContaMapper {

    public static ContaDTO toDTO(ContaModel conta) {
        if (conta == null) {
            return null;
        }

        return new ContaDTO(
                conta.getId(),
                conta.getNome(),
                conta.getDataVencimento(),
                conta.getDataPagamento(),
                conta.getValor(),
                conta.getDescricao(),
                conta.getSituacao(),
                conta.getFornecedor() != null ? conta.getFornecedor().getId() : null,
                conta.getFornecedor() != null ? conta.getFornecedor().getNome() : null
        );
    }

    public static List<ContaDTO> toDTOList(List<ContaModel> contas) {
        if (contas == null) {
            return List.of();
        }

        return contas.stream()
                .map(ContaMapper::toDTO)
                .toList();
    }

    public static ContaModel toEntity(ContaDTO dto, FornecedorModel fornecedor) {
        if (dto == null) {
            return null;
        }

        ContaModel conta = new ContaModel();

        conta.setId(dto.id());
        conta.setNome(dto.nome());
        conta.setDataVencimento(dto.dataVencimento());
        conta.setDataPagamento(dto.dataPagamento());
        conta.setValor(dto.valor());
        conta.setDescricao(dto.descricao());

        if (dto.situacao() != null) {
            conta.setSituacao(dto.situacao());
        }

        conta.setFornecedor(fornecedor);

        return conta;
    }
}