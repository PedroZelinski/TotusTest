package com.br.TotusTest.Controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.TotusTest.DTOs.ContaDTO;
import com.br.TotusTest.DTOs.ContaFiltroDTO;
import com.br.TotusTest.Services.ContaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Conta")
@Tag(name = "Contas", description = "Gestão de Contas")
public class ContaController {

    private final ContaService contaService;

    @Operation(summary = "Busca uma conta por ID", 
               description = "Retorna os dados completos de uma conta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conta encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrado")
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<ContaDTO> buscarPorId(@PathVariable Long id) {
        ContaDTO dto = contaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Lista todos as Contas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping("/FindAll")
    public ResponseEntity<List<ContaDTO>> listarTodos() {
        List<ContaDTO> lista = contaService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Cria uma nova Conta", 
               description = "Salva uma nova conta no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Conta criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/Save")
    public ResponseEntity<ContaDTO> salvar(@Valid @RequestBody ContaDTO contaDTO) {
        ContaDTO contaDTOSalvo = contaService.salvar(contaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(contaDTOSalvo);
    }

    @Operation(summary = "Atualiza uma conta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conta atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @PutMapping("/Update/{id}")
    public ResponseEntity<ContaDTO> atualizar(@PathVariable Long id, 
                                                   @Valid @RequestBody ContaDTO contaDTO) {
        ContaDTO contaDTOAtualizado = contaService.atualizar(id, contaDTO);
        return ResponseEntity.ok(contaDTOAtualizado);
    }

    @Operation(summary = "Deleta uma Conta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Conta deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<ContaDTO>> buscarContaComPaginacao(
            @RequestParam(required = false) LocalDate dataVencimentoInicio,
            @RequestParam(required = false) LocalDate dataVencimentoFim,
            @RequestParam(required = false) String descricao,
            Pageable pageable
    ) {

        ContaFiltroDTO filtro = new ContaFiltroDTO(
                dataVencimentoInicio,
                dataVencimentoFim,
                descricao
        );

        Page<ContaDTO> result = contaService.listarComFiltro(filtro, pageable);

        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/ContasPorPeriodo")
    public ResponseEntity<Double> somarValores(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim
    ) {
        Double total = contaService.somaDasContasPorPeriodo(inicio, fim);
        return ResponseEntity.ok(total);
    }
}