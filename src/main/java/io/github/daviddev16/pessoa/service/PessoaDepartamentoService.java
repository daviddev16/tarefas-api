package io.github.daviddev16.pessoa.service;

import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.pessoa.Pessoa;
import io.github.daviddev16.pessoa.PessoaDepartamento;

public interface PessoaDepartamentoService {

    Pessoa vincularDepartamentoEmPessoa(Long pessoaId, Long departamentoId);

    default Pessoa vincularDepartamentoEmPessoa(Pessoa pessoa, Departamento departamento) {
        return vincularDepartamentoEmPessoa(pessoa.getId(), departamento.getId());
    }

    Pessoa desvincularDepartamentoDePessoa(Long pessoaId, Long departamentoId);

    default Pessoa desvincularDepartamentoDePessoa(Pessoa pessoa, Departamento departamento) {
        return desvincularDepartamentoDePessoa(pessoa.getId(), departamento.getId());
    }

    PessoaDepartamento obterPessoaDepartamentoPorId(Long pessoaId, Long departamentoId);

}
