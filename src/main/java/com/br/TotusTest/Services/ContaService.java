package com.br.TotusTest.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.TotusTest.DTOs.ContaDTO;
import com.br.TotusTest.DTOs.ContaFiltroDTO;
import com.br.TotusTest.Exceptions.RecursoNaoEncontradoException;
import com.br.TotusTest.Mappers.ContaMapper;
import com.br.TotusTest.Model.ContaModel;
import com.br.TotusTest.Model.FornecedorModel;
import com.br.TotusTest.Repositories.ContaRepository;
import com.br.TotusTest.Repositories.Specifications.ContaSpecification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContaService {

    private final FornecedorService fornecedorService;
    
    private final ContaRepository contaRepository;

    @Transactional
    public ContaDTO salvar(ContaDTO dto) {

        if (dto.fornecedorId() == null) {
            throw new IllegalArgumentException("Fornecedor é obrigatório.");
        }

        log.info("Iniciando cadastro da conta '{}'.", dto.nome());

        FornecedorModel fornecedorModel = fornecedorService.buscaEntidadePorId(dto.fornecedorId());

        ContaModel entity = ContaMapper.toEntity(dto, fornecedorModel);
        ContaModel savedEntity = contaRepository.save(entity);

        log.info("Conta cadastrada com sucesso. Id: {}", savedEntity.getId());

        return ContaMapper.toDTO(savedEntity);
    }

    public ContaDTO buscarPorId(Long id) {
        log.info("Buscando conta por id: {}", id);

        ContaModel entity = contaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Conta não encontrada. Id: {}", id);
                    return new RecursoNaoEncontradoException("Conta não encontrada com id: " + id);
                });

        log.info("Conta encontrada. Id: {}", id);

        return ContaMapper.toDTO(entity);
    }

    public List<ContaDTO> listarTodos() {
        log.info("Listando todas as contas.");

        List<ContaModel> models = contaRepository.findAll();

        log.info("Foram encontradas {} conta(s).", models.size());

        return ContaMapper.toDTOList(models);
    }

    @Transactional
    public ContaDTO atualizar(Long id, ContaDTO dto) {
        log.info("Atualizando conta. Id: {}", id);

        FornecedorModel fornecedorModel = fornecedorService.buscaEntidadePorId(dto.fornecedorId());

        contaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Conta não encontrada para atualização. Id: {}", id);
                    return new RecursoNaoEncontradoException("Conta", id);
                });

        ContaModel model = ContaMapper.toEntity(dto, fornecedorModel);
        model.setId(id);

        ContaModel updated = contaRepository.save(model);

        log.info("Conta atualizada com sucesso. Id: {}", id);

        return ContaMapper.toDTO(updated);
    }

    @Transactional
    public void deletar(Long id) {
        log.info("Removendo conta. Id: {}", id);

        if (!contaRepository.existsById(id)) {
            log.warn("Conta não encontrada para remoção. Id: {}", id);
            throw new RecursoNaoEncontradoException("Conta", id);
        }

        contaRepository.deleteById(id);

        log.info("Conta removida com sucesso. Id: {}", id);
    }
    
    public Page<ContaDTO> listarComFiltro(ContaFiltroDTO filtro, Pageable pageable) {

        log.info("Listando contas com filtros e paginação.");

        Specification<ContaModel> spec = ContaSpecification.filtrar(filtro);

        Page<ContaModel> page = contaRepository.findAll(spec, pageable);

        log.info("Encontradas {} contas.", page.getTotalElements());

        return page.map(ContaMapper::toDTO);
    }
    
    public Double somaDasContasPorPeriodo(LocalDate inicio, LocalDate fim) {

        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("Datas de início e fim são obrigatórias.");
        }

        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("Data inicial não pode ser maior que a final.");
        }

        log.info("Somando valores entre {} e {}", inicio, fim);

        return contaRepository.somarValorPorPeriodo(inicio, fim);
    }
}