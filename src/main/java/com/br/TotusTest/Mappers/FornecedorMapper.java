package com.br.TotusTest.Mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.br.TotusTest.DTOs.FornecedorDTO;
import com.br.TotusTest.Model.FornecedorModel;

@Component
public class FornecedorMapper {

    public static FornecedorDTO toDTO(FornecedorModel model) {
        if (model == null) {
            return null;
        }

        return new FornecedorDTO(
            model.getId(),
            model.getNome()
        );
    }

    public static FornecedorModel toEntity(FornecedorDTO dto) {
        if (dto == null) {
            return null;
        }

        FornecedorModel model = new FornecedorModel();
        model.setId(dto.id());
        model.setNome(dto.nome());

        return model;
    }

    public static List<FornecedorDTO> toDTOList(List<FornecedorModel> models) {
        return models.stream()
                .map(FornecedorMapper::toDTO)
                .toList();
    }
}