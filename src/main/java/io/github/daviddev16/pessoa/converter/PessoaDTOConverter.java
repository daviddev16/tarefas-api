package io.github.daviddev16.pessoa.converter;

import io.github.daviddev16.departamento.converter.DepartamentoDTOConverter;
import io.github.daviddev16.departamento.response.DepartamentoResponseDTO;
import io.github.daviddev16.pessoa.Pessoa;
import io.github.daviddev16.pessoa.PessoaDepartamento;
import io.github.daviddev16.pessoa.PessoaTarefa;
import io.github.daviddev16.pessoa.dto.request.CriarPessoaRequestDTO;
import io.github.daviddev16.pessoa.dto.response.BasicoPessoaResponseDTO;
import io.github.daviddev16.pessoa.dto.response.PessoaMetricaGastoResponseDTO;
import io.github.daviddev16.pessoa.dto.response.PessoaMetricaResponseDTO;
import io.github.daviddev16.pessoa.dto.response.PessoaResponseDTO;
import io.github.daviddev16.tarefa.converter.TarefaDTOConverter;
import io.github.daviddev16.tarefa.dto.response.BasicoTarefaResponseDTO;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;

public class PessoaDTOConverter {

    public static PessoaMetricaResponseDTO converterParaPessoMetrica(Pessoa pessoa, Long tempoGastoHoras) {
        PessoaMetricaResponseDTO pessoaMetrica = new PessoaMetricaResponseDTO();
        BeanUtils.copyProperties(pessoa, pessoaMetrica);
        pessoaMetrica.setDepartamentos(converterParaDepartamentoDTOs(pessoa));
        pessoaMetrica.setTempoGastoHoras(tempoGastoHoras);
        return pessoaMetrica;
    }

    public static PessoaMetricaGastoResponseDTO converterParaPessoMetricaGasto(Pessoa pessoa, Long tempoGastoHoras,
                                                                               Double mediaDeTempoPorTarefa) {
        PessoaMetricaGastoResponseDTO pessoaMetricaGasto = new PessoaMetricaGastoResponseDTO();
        BeanUtils.copyProperties(pessoa, pessoaMetricaGasto);
        pessoaMetricaGasto.setTempoGastoHoras(tempoGastoHoras);
        pessoaMetricaGasto.setMediaDeTempoPorTarefa(mediaDeTempoPorTarefa);
        return pessoaMetricaGasto;
    }

    public static PessoaResponseDTO converterParaResponseDTO(Pessoa pessoa) {
        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();
        BeanUtils.copyProperties(pessoa, pessoaResponseDTO);
        pessoaResponseDTO.setDepartamentos(converterParaDepartamentoDTOs(pessoa));
        pessoaResponseDTO.setTarefas(converterParaBasicoTarefasDTOs(pessoa));
        return pessoaResponseDTO;
    }

    public static Pessoa converterParaPessoa(CriarPessoaRequestDTO criarPessoaRequestDTO) {
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(criarPessoaRequestDTO, pessoa);
        pessoa.setPessoaTarefas(Collections.emptySet());
        pessoa.setPessoaDepartamentos(Collections.emptySet());
        return pessoa;
    }

    public static BasicoPessoaResponseDTO converterParaBasicoResponseDTO(Pessoa pessoa) {
        BasicoPessoaResponseDTO basicoPessoaResponseDTO = new BasicoPessoaResponseDTO();
        BeanUtils.copyProperties(pessoa, basicoPessoaResponseDTO);
        return basicoPessoaResponseDTO;
    }

    private static List<DepartamentoResponseDTO> converterParaDepartamentoDTOs(Pessoa pessoa) {
        return pessoa.getPessoaDepartamentos()
                .stream()
                .map(PessoaDepartamento::getDepartamento)
                .map(DepartamentoDTOConverter::converterParaResponseDTO)
                .toList();
    }

    private static List<BasicoTarefaResponseDTO> converterParaBasicoTarefasDTOs(Pessoa pessoa) {
        return pessoa.getPessoaTarefas()
                .stream()
                .map(PessoaTarefa::getTarefa)
                .map(TarefaDTOConverter::converterParaBasicoResponseDTO)
                .toList();
    }

}
