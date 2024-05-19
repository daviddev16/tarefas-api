package io.github.daviddev16.pessoa.impl;

import io.github.daviddev16.core.GenericNaoEncontradoException;
import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.departamento.service.DepartamentoService;
import io.github.daviddev16.pessoa.Pessoa;
import io.github.daviddev16.pessoa.PessoaDepartamento;
import io.github.daviddev16.pessoa.repository.PessoaDepartamentoRepository;
import io.github.daviddev16.pessoa.service.PessoaDepartamentoService;
import io.github.daviddev16.pessoa.service.PessoaService;
import org.springframework.stereotype.Service;

public @Service class PessoaDepartamentoServiceImpl implements PessoaDepartamentoService {

    private final PessoaDepartamentoRepository pessoaDepartamentoRepository;

    private final DepartamentoService departamentoService;
    private final PessoaService pessoaService;

    public PessoaDepartamentoServiceImpl(PessoaDepartamentoRepository pessoaDepartamentoRepository,
                                         DepartamentoService departamentoService,
                                         PessoaService pessoaService) {
        this.pessoaDepartamentoRepository = pessoaDepartamentoRepository;
        this.departamentoService = departamentoService;
        this.pessoaService = pessoaService;
    }

    @Override
    public Pessoa vincularDepartamentoEmPessoa(Long pessoaId, Long departamentoId) {
        Pessoa pessoa = pessoaService.obterPessoaPorId(pessoaId);
        Departamento departamento = departamentoService.obterDepartamentoPorId(departamentoId);

        PessoaDepartamento pessoaDepartamento = new PessoaDepartamento(pessoa, departamento);
        pessoaDepartamentoRepository.save(pessoaDepartamento);

        pessoa.getPessoaDepartamentos().add(pessoaDepartamento);
        return pessoa;
    }

    @Override
    public Pessoa desvincularDepartamentoDePessoa(Long pessoaId, Long departamentoId) {
        Pessoa pessoa = pessoaService.obterPessoaPorId(pessoaId);

        PessoaDepartamento pessoaDepartamento = obterPessoaDepartamentoPorId(pessoaId, departamentoId);
        pessoaDepartamentoRepository.delete(pessoaDepartamento);

        pessoa.getPessoaDepartamentos().remove(pessoaDepartamento);
        return pessoa;
    }

    @Override
    public PessoaDepartamento obterPessoaDepartamentoPorId(Long pessoaId, Long departamentoId) {
        return pessoaDepartamentoRepository
                .findByPessoaAndDepartamentoId(pessoaId, departamentoId)
                .orElseThrow(() -> new GenericNaoEncontradoException("O usuário \"%d\" não possui" +
                        " vínculo com o departamento \"%d\".", pessoaId, departamentoId));
    }
}
