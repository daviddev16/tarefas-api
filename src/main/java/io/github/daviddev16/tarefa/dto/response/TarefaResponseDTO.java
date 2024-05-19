package io.github.daviddev16.tarefa.dto.response;

import io.github.daviddev16.departamento.response.DepartamentoResponseDTO;
import io.github.daviddev16.pessoa.dto.response.BasicoPessoaResponseDTO;
import io.github.daviddev16.tarefa.StatusTarefa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaResponseDTO extends BasicoTarefaResponseDTO {

    private List<DepartamentoResponseDTO> departamentos;
    private List<BasicoPessoaResponseDTO> pessoasAlocadas;
    private StatusTarefa statusTarefa;

}
