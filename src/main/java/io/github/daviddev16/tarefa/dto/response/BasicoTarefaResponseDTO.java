package io.github.daviddev16.tarefa.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.daviddev16.core.Utilitarios;
import io.github.daviddev16.tarefa.StatusTarefa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicoTarefaResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;

    @JsonFormat(pattern = Utilitarios.DATE_FORMAT)
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = Utilitarios.DATE_FORMAT)
    private LocalDateTime dataPrazo;

    @JsonFormat(pattern = Utilitarios.DATE_FORMAT)
    private LocalDateTime dataEncerramento;

    @JsonProperty("duracaoEmHoras")
    private Long duracaoHoras;

    private StatusTarefa statusTarefa;

}
