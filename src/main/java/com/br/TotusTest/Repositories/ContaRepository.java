package com.br.TotusTest.Repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.TotusTest.Model.ContaModel;

public interface ContaRepository extends JpaRepository<ContaModel, Long>,
JpaSpecificationExecutor<ContaModel> {

@Query("""
SELECT COALESCE(SUM(c.valor), 0)
FROM ContaModel c
WHERE c.dataVencimento BETWEEN :inicio AND :fim
""")
Double somarValorPorPeriodo(
    @Param("inicio") LocalDate inicio,
    @Param("fim") LocalDate fim
);
}
