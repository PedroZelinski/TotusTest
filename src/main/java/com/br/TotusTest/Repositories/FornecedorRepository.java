package com.br.TotusTest.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.TotusTest.Model.FornecedorModel;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorModel, Long> {

    Optional<FornecedorModel> findByNomeIgnoreCase(String nome);

    List<FornecedorModel> findByNomeContainingIgnoreCase(String nome);
}
