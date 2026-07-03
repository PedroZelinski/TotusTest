package com.br.TotusTest.Services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.TotusTest.DTOs.FornecedorDTO;
import com.br.TotusTest.Exceptions.RecursoNaoEncontradoException;
import com.br.TotusTest.Mappers.FornecedorMapper;
import com.br.TotusTest.Model.FornecedorModel;
import com.br.TotusTest.Repositories.FornecedorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    @Transactional
    public FornecedorDTO salvar(FornecedorDTO dto) {
        log.info("Iniciando cadastro do fornecedor '{}'.", dto.nome());

        FornecedorModel entity = FornecedorMapper.toEntity(dto);
        FornecedorModel savedEntity = fornecedorRepository.save(entity);

        log.info("Fornecedor cadastrado com sucesso. Id: {}", savedEntity.getId());

        return FornecedorMapper.toDTO(savedEntity);
    }

    public FornecedorDTO buscarPorId(Long id) {
        log.info("Buscando fornecedor por id: {}", id);

        FornecedorModel entity = fornecedorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Fornecedor não encontrado. Id: {}", id);
                    return new RecursoNaoEncontradoException("Fornecedor não encontrado com id: " + id);
                });

        log.info("Fornecedor encontrado. Id: {}", id);

        return FornecedorMapper.toDTO(entity);
    }

    public FornecedorModel buscaEntidadePorId(Long id) {
        log.debug("Buscando entidade Fornecedor por id: {}", id);

        return fornecedorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Fornecedor não encontrado. Id: {}", id);
                    return new RecursoNaoEncontradoException("Fornecedor não encontrado com id: " + id);
                });
    }

    public List<FornecedorDTO> listarTodos() {
        log.info("Listando todos os fornecedores.");

        List<FornecedorModel> models = fornecedorRepository.findAll();

        log.info("Foram encontrados {} fornecedor(es).", models.size());

        return FornecedorMapper.toDTOList(models);
    }

    @Transactional
    public FornecedorDTO atualizar(Long id, FornecedorDTO dto) {
        log.info("Atualizando fornecedor. Id: {}", id);

        fornecedorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Fornecedor não encontrado para atualização. Id: {}", id);
                    return new RecursoNaoEncontradoException("Fornecedor", id);
                });

        FornecedorModel model = FornecedorMapper.toEntity(dto);
        model.setId(id);

        FornecedorModel updated = fornecedorRepository.save(model);

        log.info("Fornecedor atualizado com sucesso. Id: {}", id);

        return FornecedorMapper.toDTO(updated);
    }

    @Transactional
    public void deletar(Long id) {
        log.info("Removendo fornecedor. Id: {}", id);

        if (!fornecedorRepository.existsById(id)) {
            log.warn("Fornecedor não encontrado para remoção. Id: {}", id);
            throw new RecursoNaoEncontradoException("Fornecedor", id);
        }

        fornecedorRepository.deleteById(id);

        log.info("Fornecedor removido com sucesso. Id: {}", id);
    }
}
