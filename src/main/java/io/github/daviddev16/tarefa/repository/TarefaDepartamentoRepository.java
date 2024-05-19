package io.github.daviddev16.tarefa.repository;

import io.github.daviddev16.tarefa.TarefaDepartamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public @Repository interface TarefaDepartamentoRepository extends JpaRepository<TarefaDepartamento, Long> {

    @Query("FROM TarefaDepartamento TD WHERE TD.tarefa.id = :paramTarefaId AND TD.departamento.id = :paramDepartamentoId")
    Optional<TarefaDepartamento> findByTarefaAndDepartamentoId(
            @Param("paramTarefaId") Long tarefaId,
            @Param("paramDepartamentoId")Long departamentoId);

}
