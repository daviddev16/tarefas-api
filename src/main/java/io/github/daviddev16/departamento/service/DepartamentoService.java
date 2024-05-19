package io.github.daviddev16.departamento.service;

import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.departamento.request.CriarDepartamentoRequestDTO;

import java.util.List;
import java.util.Set;

public interface DepartamentoService {

    default void excluirDepartamentoPorId(Long id) {
        Departamento departamento = obterDepartamentoPorId(id);
        excluirDepartamento(departamento);
    }

    void excluirDepartamento(Departamento departamento);

    Departamento criarDepartamento(CriarDepartamentoRequestDTO criarDepartamentoRequestDTO);

    Departamento obterDepartamentoPorId(Long id);

    Departamento obterDepartamentoPorNome(String nome);

    List<Departamento> obterDepartamentosPorNomes(Set<String> departamentos);

    List<Departamento> obterDepartamentos();


}
