package io.github.daviddev16.tarefa;

import io.github.daviddev16.pessoa.PessoaTarefa;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static io.github.daviddev16.tarefa.Tarefa.TITULO_CONSTRAINT_NAME;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(
        name = "tb_tarefa",
        uniqueConstraints = {
                @UniqueConstraint(name = TITULO_CONSTRAINT_NAME, columnNames = "titulo")}
)
public @Entity class Tarefa {

    public static final String TITULO_CONSTRAINT_NAME = "tb_tarefa_titulo_uq";

    @Id
    @SequenceGenerator(
            name = "idtarefa_sequence_gen",
            sequenceName = "tb_tarefa_idtarefa_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "idtarefa_sequence_gen",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "idtarefa",
            nullable = false
    )
    private Long id;

    @Column(
            name = "titulo",
            nullable = false,
            length = 100
    )
    private String titulo;

    @Column(
            name = "dstarefa",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String descricao;

    @Column(
            name = "dtprazo",
            nullable = false
    )
    private LocalDateTime dataPrazo;

    @Column(
            name = "dtcriacao",
            nullable = false
    )
    private LocalDateTime dataCriacao;

    @Column(
            name = "dtencerramento"
    )
    private LocalDateTime dataEncerramento;

    @Column(
            name = "hrduracao"
    )
    private Long duracaoHoras;

    @Column(
            name = "statustarefa",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private StatusTarefa statusTarefa;

    @OneToMany(mappedBy = "tarefa", fetch = FetchType.EAGER)
    private List<TarefaDepartamento> tarefaDepartamentos;

    @OneToMany(mappedBy = "tarefa", fetch = FetchType.EAGER)
    private List<PessoaTarefa> pessoasAlocadas;

    public static boolean validarFinalizacao(Tarefa tarefa) {
        return tarefa.getStatusTarefa() == StatusTarefa.FINALIZADA;
    }

}
