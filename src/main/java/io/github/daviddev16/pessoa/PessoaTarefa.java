package io.github.daviddev16.pessoa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.daviddev16.tarefa.Tarefa;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(
        name = "tb_pessoa_tarefa",
        indexes = {
                @Index(name = "tb_pessoa_tarefa_idpessoatarefa_idx", columnList = "idpessoa, idtarefa")}
)
public @Entity class PessoaTarefa {

    @Id
    @SequenceGenerator(
            name = "idpessoatarefa_sequence_gen",
            sequenceName = "tb_pessoa_tarefa_idpessoatarefa_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "idpessoatarefa_sequence_gen",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "idpessoatarefa",
            nullable = false
    )
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "idpessoa",
            referencedColumnName = "idpessoa",
            nullable = false
    )
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(
            name = "idtarefa",
            referencedColumnName = "idtarefa",
            nullable = false
    )
    private Tarefa tarefa;

    public PessoaTarefa(Pessoa pessoa, Tarefa tarefa) {
        this.pessoa = pessoa;
        this.tarefa = tarefa;
    }

}
