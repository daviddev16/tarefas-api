package io.github.daviddev16.tarefa.converter;

import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.departamento.converter.DepartamentoDTOConverter;
import io.github.daviddev16.departamento.response.DepartamentoResponseDTO;
import io.github.daviddev16.pessoa.PessoaTarefa;
import io.github.daviddev16.pessoa.converter.PessoaDTOConverter;
import io.github.daviddev16.pessoa.dto.response.BasicoPessoaResponseDTO;
import io.github.daviddev16.tarefa.StatusTarefa;
import io.github.daviddev16.tarefa.Tarefa;
import io.github.daviddev16.tarefa.TarefaDepartamento;
import io.github.daviddev16.tarefa.dto.request.CriarTarefaRequestDTO;
import io.github.daviddev16.tarefa.dto.response.BasicoTarefaResponseDTO;
import io.github.daviddev16.tarefa.dto.response.TarefaResponseDTO;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TarefaDTOConverter {

    public static TarefaResponseDTO converterParaResponseDTO(Tarefa tarefa) {
        TarefaResponseDTO tarefaResponseDTO = new TarefaResponseDTO();
        BeanUtils.copyProperties(tarefa, tarefaResponseDTO);
        tarefaResponseDTO.setDepartamentos(obterDepartamentoDTODeTarefa(tarefa));
        tarefaResponseDTO.setPessoasAlocadas(obterPessoasAlocadasDTOParaTarefa(tarefa));
        return tarefaResponseDTO;
    }

    public static BasicoTarefaResponseDTO converterParaBasicoResponseDTO(Tarefa tarefa) {
        BasicoTarefaResponseDTO tarefaResponseDTO = new BasicoTarefaResponseDTO();
        BeanUtils.copyProperties(tarefa, tarefaResponseDTO);
        return tarefaResponseDTO;
    }

    public static Tarefa converterParaTarefa(CriarTarefaRequestDTO criarTarefaRequestDTO) {
        Tarefa tarefa = new Tarefa();
        BeanUtils.copyProperties(criarTarefaRequestDTO, tarefa);
        tarefa.setPessoasAlocadas(Collections.emptyList());
        tarefa.setTarefaDepartamentos(Collections.emptyList());
        tarefa.setStatusTarefa(StatusTarefa.ABERTA);
        return tarefa;
    }

    public static List<DepartamentoResponseDTO> obterDepartamentoDTODeTarefa(Tarefa tarefa) {
        return obterDepartamentoDeTarefa(tarefa)
                .stream()
                .map(DepartamentoDTOConverter::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    public static List<Departamento> obterDepartamentoDeTarefa(Tarefa tarefa) {
        return tarefa
                .getTarefaDepartamentos()
                .stream()
                .map(TarefaDepartamento::getDepartamento)
                .collect(Collectors.toList());
    }

    public static List<BasicoPessoaResponseDTO> obterPessoasAlocadasDTOParaTarefa(Tarefa tarefa) {
        return tarefa
                .getPessoasAlocadas()
                .stream()
                .map(PessoaTarefa::getPessoa)
                .map(PessoaDTOConverter::converterParaBasicoResponseDTO)
                .collect(Collectors.toList());
    }


}
