package io.github.daviddev16.pessoa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.daviddev16.departamento.Departamento;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(
        name = "tb_pessoa_departamento",
        indexes = {
                @Index(name = "tb_pessoa_departamento_idpessoadepartamento_idx", columnList = "idpessoa, iddepartamento")}
)
public @Entity class PessoaDepartamento {

    @Id
    @SequenceGenerator(
            name = "idpessoadepartamento_sequence_gen",
            sequenceName = "tb_pessoa_departamento_idpessoadepartamento_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "idpessoadepartamento_sequence_gen",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "idpessoadepartamento",
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
            name = "iddepartamento",
            referencedColumnName = "iddepartamento",
            nullable = false
    )
    private Departamento departamento;

    public PessoaDepartamento(Pessoa pessoa, Departamento departamento) {
        this.pessoa = pessoa;
        this.departamento = departamento;
    }

}
