package io.github.daviddev16.pessoa.dto.response;

import io.github.daviddev16.departamento.response.DepartamentoResponseDTO;
import io.github.daviddev16.tarefa.dto.response.BasicoTarefaResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponseDTO extends BasicoPessoaResponseDTO {

    private List<DepartamentoResponseDTO> departamentos;
    private List<BasicoTarefaResponseDTO> tarefas;

}
