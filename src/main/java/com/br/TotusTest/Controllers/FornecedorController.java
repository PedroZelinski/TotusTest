package com.br.TotusTest.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.TotusTest.DTOs.FornecedorDTO;
import com.br.TotusTest.Services.FornecedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fornecedor")
@Tag(name = "Fornecedores", description = "Gestão de Fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @Operation(summary = "Busca um fornecedor por ID", 
               description = "Retorna os dados completos de um fornecedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<FornecedorDTO> buscarPorId(@PathVariable Long id) {
        FornecedorDTO dto = fornecedorService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Lista todos os fornecedores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping("/FindAll")
    public ResponseEntity<List<FornecedorDTO>> listarTodos() {
        List<FornecedorDTO> lista = fornecedorService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Cria um novo fornecedor", 
               description = "Salva um novo fornecedor no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Fornecedor criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/Save/{id}")
    public ResponseEntity<FornecedorDTO> salvar(@Valid @RequestBody FornecedorDTO fornecedorDTO) {
        FornecedorDTO fornecedorDTOSalvo = fornecedorService.salvar(fornecedorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorDTOSalvo);
    }

    @Operation(summary = "Atualiza um fornecedor existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    @PutMapping("/Update/{id}")
    public ResponseEntity<FornecedorDTO> atualizar(@PathVariable Long id, 
                                                   @Valid @RequestBody FornecedorDTO fornecedorDTO) {
        FornecedorDTO fornecedorDTOAtualizado = fornecedorService.atualizar(id, fornecedorDTO);
        return ResponseEntity.ok(fornecedorDTOAtualizado);
    }

    @Operation(summary = "Deleta um fornecedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Fornecedor deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        fornecedorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}