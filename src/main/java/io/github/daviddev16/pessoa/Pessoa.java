package io.github.daviddev16.pessoa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

import static io.github.daviddev16.pessoa.Pessoa.NMPESSOA_CONSTRAINT_NAME;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(
        name = "tb_pessoa",
        uniqueConstraints = {
                @UniqueConstraint(name = NMPESSOA_CONSTRAINT_NAME, columnNames = "nmpessoa")}
)
public @Entity class Pessoa {

    public static final String NMPESSOA_CONSTRAINT_NAME = "tb_pessoa_nmpessoa_uq";

    @Id
    @SequenceGenerator(
            name = "idpessoa_sequence_gen",
            sequenceName = "tb_pessoa_idpessoa_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "idpessoa_sequence_gen",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "idpessoa",
            nullable = false
    )
    private Long id;

    @Column(
            name = "nmpessoa",
            nullable = false
    )
    private String nome;

    @OneToMany(
            mappedBy = "pessoa",
            fetch = FetchType.EAGER
    )
    private Set<PessoaTarefa> pessoaTarefas;

    @OneToMany(
            mappedBy = "pessoa",
            cascade = { CascadeType.ALL },
            fetch = FetchType.EAGER
    )
    private Set<PessoaDepartamento> pessoaDepartamentos;

    @Column(
            name = "dtcriacao",
            columnDefinition = "TIMESTAMP DEFAULT NOW()",
            nullable = false
    )
    private LocalDateTime dataCriacao;

    public Pessoa(String nome) {
        this.nome = nome;
    }

}
