package io.github.daviddev16.tarefa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.daviddev16.departamento.Departamento;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(
        name = "tb_tarefa_departamento",
        indexes = {
                @Index(name = "tb_tarefa_departamento_idtarefadepartamento_idx", columnList = "idtarefa, iddepartamento")}
)
public @Entity class TarefaDepartamento {

    @Id
    @SequenceGenerator(
            name = "idtarefadepartamento_sequence_gen",
            sequenceName = "tb_tarefa_departamento_idtarefadepartamento_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "idtarefadepartamento_sequence_gen",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "idtarefadepartamento",
            nullable = false
    )
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "idtarefa",
            referencedColumnName = "idtarefa",
            nullable = false
    )
    private Tarefa tarefa;

    @ManyToOne
    @JoinColumn(
            name = "iddepartamento",
            referencedColumnName = "iddepartamento",
            nullable = false
    )
    private Departamento departamento;

    public TarefaDepartamento(Tarefa tarefa, Departamento departamento) {
        this.tarefa = tarefa;
        this.departamento = departamento;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TarefaDepartamento that = (TarefaDepartamento) object;
        if (!Objects.equals(tarefa, that.tarefa)) return false;
        return Objects.equals(departamento, that.departamento);
    }

    @Override
    public int hashCode() {
        int result = tarefa != null ? tarefa.hashCode() : 0;
        result = 31 * result + (departamento != null ? departamento.hashCode() : 0);
        return result;
    }

}