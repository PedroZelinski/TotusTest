package com.br.TotusTest.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.TotusTest.DTOs.CSVDTO;
import com.br.TotusTest.Services.CSVService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/importacao")
@RequiredArgsConstructor
public class ImportadorController {

    private final CSVService cSVService;

    @PostMapping("/csv")
    public ResponseEntity<CSVDTO> upload(@RequestParam("file") MultipartFile file) {

        return ResponseEntity.accepted()
                .body(cSVService.receberArquivo(file));
    }
}