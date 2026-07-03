package com.br.TotusTest.Repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.br.TotusTest.Model.CSVModel;

public interface CSVRepository extends JpaRepository<CSVModel, Long> {

    Optional<CSVModel> findByProtocolo(String protocolo);

}