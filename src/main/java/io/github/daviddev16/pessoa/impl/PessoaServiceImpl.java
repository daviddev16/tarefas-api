package io.github.daviddev16.pessoa.impl;

import io.github.daviddev16.core.GenericNaoEncontradoException;
import io.github.daviddev16.core.ServicoException;
import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.departamento.service.DepartamentoService;
import io.github.daviddev16.pessoa.Pessoa;
import io.github.daviddev16.pessoa.PessoaDepartamento;
import io.github.daviddev16.pessoa.dto.OpcoesBuscaPessoaDTO;
import io.github.daviddev16.pessoa.dto.response.PessoaMetricaGastoResponseDTO;
import io.github.daviddev16.pessoa.dto.response.PessoaMetricaResponseDTO;
import io.github.daviddev16.pessoa.PessoaTarefa;
import io.github.daviddev16.pessoa.converter.PessoaDTOConverter;
import io.github.daviddev16.pessoa.dto.request.AlterarPessoaRequestDTO;
import io.github.daviddev16.pessoa.dto.request.CriarPessoaRequestDTO;
import io.github.daviddev16.pessoa.exception.PessoaNaoEncontradaException;
import io.github.daviddev16.pessoa.repository.PessoaRepository;
import io.github.daviddev16.pessoa.repository.PessoaTarefaRepository;
import io.github.daviddev16.pessoa.service.PessoaService;
import io.github.daviddev16.pessoa.specification.PessoaSpecification;
import io.github.daviddev16.tarefa.Tarefa;
import io.github.daviddev16.tarefa.converter.TarefaDTOConverter;
import io.github.daviddev16.tarefa.service.TarefaService;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.LongStream;

public @Service class PessoaServiceImpl implements PessoaService {

    private final EntityManager entityManager;

    private final PessoaRepository pessoaRepository;
    private final PessoaTarefaRepository pessoaTarefaRepository;

    private final TarefaService tarefaService;
    private final DepartamentoService departamentoService;

    public PessoaServiceImpl(EntityManager entityManager,
                             PessoaRepository pessoaRepository,
                             PessoaTarefaRepository pessoaTarefaRepository,
                             TarefaService tarefaService,
                             DepartamentoService departamentoService)
    {
        this.entityManager = entityManager;
        this.pessoaRepository = pessoaRepository;
        this.pessoaTarefaRepository = pessoaTarefaRepository;
        this.tarefaService = tarefaService;
        this.departamentoService = departamentoService;
    }

    @Override
    public Pessoa criarPessoa(CriarPessoaRequestDTO criarPessoaRequestDTO) {
        Pessoa pessoa = PessoaDTOConverter.converterParaPessoa(criarPessoaRequestDTO);
        pessoa.setDataCriacao(LocalDateTime.now());
        pessoa.setPessoaDepartamentos(Collections.emptySet());
        pessoa.setPessoaTarefas(Collections.emptySet());
        pessoaRepository.saveSafely(pessoa);
        return pessoa;
    }

    @Override
    public Pessoa obterPessoaDisponivelParaTarefaPorId(Long tarefaId) {
        return pessoaRepository.encontrarPessoaDisponivelParaTarefa(tarefaId)
                .orElseThrow(() -> new GenericNaoEncontradoException("Não foi possível " +
                        "localizar uma nova pessoa disponível para a tarefa \"%s\".", tarefaId));
    }

    @Override
    public Tarefa alocarPessoaEmTarefaPorId(Long pessoaId, Long tarefaId) {
        Tarefa tarefa = tarefaService.obterTarefaPorId(tarefaId);

        if (Tarefa.validarFinalizacao(tarefa))
            throw new ServicoException("Tarefa \"%d\" foi encerrada anteriormente.", tarefaId);

        Pessoa pessoaAlocada = obterPessoaPorId(pessoaId);

        if (!validarDepartamentoDePessoa(pessoaAlocada, tarefa))
            throw new ServicoException("A tarefa\"%s\" não pertence aos " +
                    "departamentos da pessoa \"%s\".", tarefa.getTitulo(), pessoaAlocada.getNome());

        tarefa.getPessoasAlocadas()
                .add(criarPessoaTarefa(pessoaAlocada, tarefa));

        return tarefa;
    }

    private PessoaTarefa criarPessoaTarefa(Pessoa pessoa, Tarefa tarefa) {
        return pessoaTarefaRepository.save(new PessoaTarefa(pessoa, tarefa));
    }

    private boolean validarDepartamentoDePessoa(Pessoa pessoa, Tarefa tarefa) {
        List<Departamento> departamentoDeTarefa = TarefaDTOConverter
                .obterDepartamentoDeTarefa(tarefa);

        return pessoa.getPessoaDepartamentos()
                .stream()
                .map(PessoaDepartamento::getDepartamento)
                .anyMatch(departamentoDeTarefa::contains);
    }

    @Override
    public List<PessoaMetricaResponseDTO> obterListaPessoaMetrica() {
        return obterPessoas()
                .stream()
                .map(pessoa -> PessoaDTOConverter
                        .converterParaPessoMetrica(pessoa, obterSomatorioTempoGastoDePessoa(pessoa)))
                .toList();
    }

    @Override
    public List<PessoaMetricaGastoResponseDTO> obterListaPessoaMetricaGasto(OpcoesBuscaPessoaDTO opcoesBuscaPessoaDTO) {
        Specification<Pessoa> specificationBuscaPessoa = PessoaSpecification
                .buscarPessoaPorEspecificacao(opcoesBuscaPessoaDTO);

        return pessoaRepository
                .findAll(specificationBuscaPessoa)
                .stream()
                .map(pessoa -> {
                    Long tempoGasto = obterSomatorioTempoGastoDePessoa(pessoa);
                    Double media = obterMediaTempoGastoDePessoa(pessoa);
                    return PessoaDTOConverter.converterParaPessoMetricaGasto(pessoa, tempoGasto, media);
                }).toList();
    }

    private Long obterSomatorioTempoGastoDePessoa(Pessoa pessoa) {
        return obterDuracaoHorasStream(pessoa)
                .sum();
    }

    private Double obterMediaTempoGastoDePessoa(Pessoa pessoa) {
        return obterDuracaoHorasStream(pessoa)
                .average()
                .orElse(0);
    }

    private LongStream obterDuracaoHorasStream(Pessoa pessoa) {
        return pessoa.getPessoaTarefas()
                .stream()
                .map(PessoaTarefa::getTarefa)
                .mapToLong(Tarefa::getDuracaoHoras);
    }

    @Override
    @Transactional
    public Pessoa alterarPessoaPorId(Long id, AlterarPessoaRequestDTO alterarPessoaRequestDTO) {
        Pessoa pessoa = obterPessoaPorId(id);
        pessoa.setNome(alterarPessoaRequestDTO.getNome());
        return pessoaRepository.saveSafely(pessoa);
    }

    @Override
    public Pessoa obterPessoaPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException(id));
    }

    @Override
    public Pessoa obterPessoaPorNome(String nome) {
        return pessoaRepository.findByNome(nome)
                .orElseThrow(() -> new PessoaNaoEncontradaException(nome));
    }

    @Override
    public List<Pessoa> obterPessoas() {
        return pessoaRepository.findAll();
    }

    @Override
    public void excluirPessoa(Pessoa pessoa) {
        pessoaRepository.delete(pessoa);
    }


}
