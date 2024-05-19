package io.github.daviddev16.tarefa.repository;

import io.github.daviddev16.core.ServicoException;
import io.github.daviddev16.core.persistence.JpaSafeRepository;
import io.github.daviddev16.tarefa.Tarefa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public @Repository interface TarefaRepository extends JpaSafeRepository<Tarefa, Long> {

    Optional<Tarefa> findByTitulo(String titulo);

    @Query(
            nativeQuery = true,
            value =
                    """
                        SELECT
                            TA.*
                        FROM
                            tb_tarefa TA
                        LEFT JOIN
                            tb_pessoa_tarefa PT
                            ON (TA.idtarefa = PT.idtarefa AND PT.idpessoa IS NULL)
                        ORDER BY
                            TA.dtprazo ASC
                        LIMIT :paramQtTarefas
                    """
    )
    List<Tarefa> encontrarTarefasPendentes(@Param("paramQtTarefas") Integer quantidadeTarefas);

    @Override
    default void handleConstraintViolation(String constraintName, Tarefa tarefa) {
        if (constraintName.equals(Tarefa.TITULO_CONSTRAINT_NAME))
            throw new ServicoException("O título \"%s\" já está em uso.", tarefa.getTitulo());
    }
}
