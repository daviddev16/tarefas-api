package io.github.daviddev16.departamento;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(
        name = "tb_departamento",
        uniqueConstraints = {
                @UniqueConstraint(name = "tb_departamento_nmdepartamento_uq", columnNames = "nmdepartamento")}
)
public @Entity class Departamento {

    public static final String NMDEPARTAMENTO_CONSTRAINT_NAME = "tb_departamento_nmdepartamento_uq";

    @Id
    @SequenceGenerator(
            name = "iddepartamento_sequence_gen",
            sequenceName = "tb_departamento_iddepartamento_seq",
            allocationSize = 1
    )
    @Column(
            name = "iddepartamento",
            nullable = false
    )
    @GeneratedValue(
            generator = "iddepartamento_sequence_gen",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Column(
            name = "nmdepartamento",
            nullable = false
    )
    private String nome;

    public Departamento(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Departamento that = (Departamento) object;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
