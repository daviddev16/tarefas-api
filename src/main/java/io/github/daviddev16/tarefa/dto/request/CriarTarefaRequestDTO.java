package io.github.daviddev16.tarefa.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.daviddev16.core.Utilitarios;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriarTarefaRequestDTO {

    private String titulo;
    private String descricao;

    @JsonFormat(pattern= Utilitarios.DATE_FORMAT_TIME)
    private LocalDateTime dataPrazo;

    private Set<String> departamentos;

}
