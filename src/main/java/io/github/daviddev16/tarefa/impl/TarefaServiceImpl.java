package io.github.daviddev16.tarefa.impl;

import io.github.daviddev16.core.ServicoException;
import io.github.daviddev16.departamento.projection.DepartamentoSumarioProjection;
import io.github.daviddev16.departamento.repository.DepartamentoRepository;
import io.github.daviddev16.departamento.service.DepartamentoService;
import io.github.daviddev16.tarefa.StatusTarefa;
import io.github.daviddev16.tarefa.Tarefa;
import io.github.daviddev16.tarefa.TarefaDepartamento;
import io.github.daviddev16.tarefa.converter.TarefaDTOConverter;
import io.github.daviddev16.tarefa.dto.request.CriarTarefaRequestDTO;
import io.github.daviddev16.tarefa.exception.TarefaNaoEncontradaException;
import io.github.daviddev16.tarefa.repository.TarefaDepartamentoRepository;
import io.github.daviddev16.tarefa.repository.TarefaRepository;
import io.github.daviddev16.tarefa.service.TarefaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public @Service class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaDepartamentoRepository tarefaDepartamentoRepository;
    private final DepartamentoRepository departamentoRepository;

    private final DepartamentoService departamentoService;

    public TarefaServiceImpl(TarefaRepository tarefaRepository,
                             TarefaDepartamentoRepository tarefaDepartamentoRepository,
                             DepartamentoRepository departamentoRepository,
                             DepartamentoService departamentoService) {
        this.tarefaRepository = tarefaRepository;
        this.tarefaDepartamentoRepository = tarefaDepartamentoRepository;
        this.departamentoRepository = departamentoRepository;
        this.departamentoService = departamentoService;
    }

    @Override
    @Transactional
    public Tarefa criarTarefa(CriarTarefaRequestDTO criarTarefaRequestDTO) {
        LocalDateTime dataCriacao = LocalDateTime.now();
        Tarefa tarefa = TarefaDTOConverter.converterParaTarefa(criarTarefaRequestDTO);

        if (dataCriacao.isAfter(tarefa.getDataPrazo()))
            throw new ServicoException("A data de prazo não pode ser menor a data atual.");

        tarefa.setDataCriacao(dataCriacao);
        tarefaRepository.saveSafely(tarefa);

        List<TarefaDepartamento> tarefaDepartamentoList = departamentoService
                .obterDepartamentosPorNomes(criarTarefaRequestDTO.getDepartamentos())
                .stream()
                .map(departamento -> new TarefaDepartamento(tarefa, departamento))
                .toList();

        tarefa.setTarefaDepartamentos(
                tarefaDepartamentoRepository.saveAll(tarefaDepartamentoList));

        return tarefa;
    }

    @Override
    public List<DepartamentoSumarioProjection> obterSumarioDepartamentos() {
        return departamentoRepository.obterSumarioDepartamentos();
    }

    @Override
    public Tarefa encerrarTarefaPorId(Long id) {
        LocalDateTime dataEncerramento = LocalDateTime.now();
        Tarefa tarefa = obterTarefaPorId(id);

        if (tarefa.getStatusTarefa() == StatusTarefa.FINALIZADA)
            throw new ServicoException("Tarefa \"%d\" foi encerrada anteriormente.", id);

        tarefa.setStatusTarefa(StatusTarefa.FINALIZADA);
        tarefa.setDuracaoHoras(calcularDuracaoEmHoras(dataEncerramento, tarefa.getDataCriacao()));
        tarefa.setDataEncerramento(dataEncerramento);

        return tarefaRepository.saveSafely(tarefa);
    }

    private Long calcularDuracaoEmHoras(LocalDateTime dataEncerramento, LocalDateTime dataCriacao) {
        return Duration.between(dataCriacao, dataEncerramento).toHours();
    }

    @Override
    public Tarefa obterTarefaPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException(id));
    }

    @Override
    public List<Tarefa> obterTarefasPendentes(Integer numeroTarefas) {
        numeroTarefas = (numeroTarefas != null) ? numeroTarefas : 3;
        return tarefaRepository.encontrarTarefasPendentes(numeroTarefas);
    }

    @Override
    public void excluirTarefa(Tarefa tarefa) {
        tarefaRepository.delete(tarefa);
    }

    @Override
    public List<Tarefa> obterTarefas() {
        return tarefaRepository.findAll();
    }
}
