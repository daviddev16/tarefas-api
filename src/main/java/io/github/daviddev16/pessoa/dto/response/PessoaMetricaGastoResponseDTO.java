package io.github.daviddev16.pessoa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaMetricaGastoResponseDTO  extends BasicoPessoaResponseDTO {

    private Long tempoGastoHoras;
    private Double mediaDeTempoPorTarefa;

}
