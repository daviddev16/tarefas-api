package io.github.daviddev16.pessoa.service;

import io.github.daviddev16.pessoa.Pessoa;
import io.github.daviddev16.pessoa.dto.OpcoesBuscaPessoaDTO;
import io.github.daviddev16.pessoa.dto.request.AlterarPessoaRequestDTO;
import io.github.daviddev16.pessoa.dto.request.CriarPessoaRequestDTO;
import io.github.daviddev16.pessoa.dto.response.PessoaMetricaGastoResponseDTO;
import io.github.daviddev16.pessoa.dto.response.PessoaMetricaResponseDTO;
import io.github.daviddev16.tarefa.Tarefa;

import java.util.List;

public interface PessoaService {

    Pessoa obterPessoaPorId(Long id);

    Pessoa obterPessoaDisponivelParaTarefaPorId(Long tarefaId);

    List<PessoaMetricaResponseDTO> obterListaPessoaMetrica();

    List<PessoaMetricaGastoResponseDTO> obterListaPessoaMetricaGasto(OpcoesBuscaPessoaDTO opcoesBuscaPessoaDTO);

    Pessoa obterPessoaPorNome(String nome);

    List<Pessoa> obterPessoas();

    Pessoa criarPessoa(CriarPessoaRequestDTO criarPessoaRequestDTO);

    Pessoa alterarPessoaPorId(Long id, AlterarPessoaRequestDTO alterarPessoaRequestDTO);

    Tarefa alocarPessoaEmTarefaPorId(Long pessoaId, Long tarefaId);

    void excluirPessoa(Pessoa pessoa);

    default void excluirPessoaPorId(Long id) {
        Pessoa pessoa = obterPessoaPorId(id);
        excluirPessoa(pessoa);
    }

}
