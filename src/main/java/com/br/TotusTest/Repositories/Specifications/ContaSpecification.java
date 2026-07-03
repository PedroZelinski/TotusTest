package com.br.TotusTest.Repositories.Specifications;

import org.springframework.data.jpa.domain.Specification;

import com.br.TotusTest.DTOs.ContaFiltroDTO;
import com.br.TotusTest.Model.ContaModel;

import jakarta.persistence.criteria.Predicate;

public class ContaSpecification {

    public static Specification<ContaModel> filtrar(ContaFiltroDTO filtro) {
        return (root, query, cb) -> {

            Predicate predicate = cb.conjunction();

            if (filtro.dataVencimentoInicio() != null) {
                predicate = cb.and(predicate,
                        cb.greaterThanOrEqualTo(
                                root.get("dataVencimento"),
                                filtro.dataVencimentoInicio()
                        ));
            }

            if (filtro.dataVencimentoFim() != null) {
                predicate = cb.and(predicate,
                        cb.lessThanOrEqualTo(
                                root.get("dataVencimento"),
                                filtro.dataVencimentoFim()
                        ));
            }

            if (filtro.descricao() != null && !filtro.descricao().isBlank()) {
                predicate = cb.and(predicate,
                        cb.like(
                                cb.lower(root.get("descricao")),
                                "%" + filtro.descricao().toLowerCase() + "%"
                        ));
            }

            return predicate;
        };
    }
}