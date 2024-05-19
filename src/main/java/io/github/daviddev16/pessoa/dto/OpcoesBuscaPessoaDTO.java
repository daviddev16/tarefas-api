package io.github.daviddev16.pessoa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.daviddev16.core.Utilitarios;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpcoesBuscaPessoaDTO {

    private String nome;

    @JsonFormat(pattern = Utilitarios.DATE_FORMAT)
    private LocalDate dataCriacaoInicial;

    @JsonFormat(pattern = Utilitarios.DATE_FORMAT)
    private LocalDate dataCriacaoFinal;

}
