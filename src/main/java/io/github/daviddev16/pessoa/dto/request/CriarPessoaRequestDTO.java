package io.github.daviddev16.pessoa.dto.request;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriarPessoaRequestDTO {

    private String nome;
    private Set<String> departamentos;

    public CriarPessoaRequestDTO(String nome) {
        this.nome = nome;
    }

}

