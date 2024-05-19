package io.github.daviddev16.departamento.impl;

import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.departamento.converter.DepartamentoDTOConverter;
import io.github.daviddev16.departamento.request.CriarDepartamentoRequestDTO;
import io.github.daviddev16.departamento.exception.DepartamentoNaoEncontradoException;
import io.github.daviddev16.departamento.repository.DepartamentoRepository;
import io.github.daviddev16.departamento.service.DepartamentoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public @Service class DepartamentoServiceImpl implements DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoServiceImpl(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    @Override
    public List<Departamento> obterDepartamentos() {
        return departamentoRepository.findAll();
    }

    @Override
    public Departamento obterDepartamentoPorId(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new DepartamentoNaoEncontradoException(id));
    }

    @Override
    public Departamento obterDepartamentoPorNome(String nome) {
        return departamentoRepository.findByNome(nome)
                .orElseThrow(() -> new DepartamentoNaoEncontradoException(nome));
    }

    @Override
    public List<Departamento> obterDepartamentosPorNomes(Set<String> departamentos) {
        return departamentoRepository.findAllByNomes(departamentos);
    }

    @Override
    public Departamento criarDepartamento(CriarDepartamentoRequestDTO criarDepartamentoRequestDTO) {
        Departamento departamento = DepartamentoDTOConverter
                .converterParaDepartamento(criarDepartamentoRequestDTO);
        return departamentoRepository.saveSafely(departamento);
    }

    @Override
    public void excluirDepartamento(Departamento departamento) {
        departamentoRepository.delete(departamento);
    }
}
