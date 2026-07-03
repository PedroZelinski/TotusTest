package com.br.TotusTest.Model;

import java.time.LocalDateTime;

import com.br.TotusTest.DTOs.Util.StatusImportacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "csv_importacao")
@NoArgsConstructor
@Getter
@Setter
public class CSVModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String protocolo;

    @Column(nullable = false)
    private String nomeArquivo;

    @Column(nullable = false)
    private String caminhoArquivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusImportacao status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;

    private String mensagemErro;

    public static CSVModel criar(String protocolo, String nomeArquivo, String caminhoArquivo) {
        CSVModel model = new CSVModel();
        model.setProtocolo(protocolo);
        model.setNomeArquivo(nomeArquivo);
        model.setCaminhoArquivo(caminhoArquivo);
        model.setStatus(StatusImportacao.RECEBIDO);
        model.setCriadoEm(LocalDateTime.now());
        return model;
    }
}