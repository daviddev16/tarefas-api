package io.github.daviddev16.pessoa.repository;

import io.github.daviddev16.pessoa.PessoaTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public @Repository interface PessoaTarefaRepository extends JpaRepository<PessoaTarefa, Long> {

    @Query("FROM PessoaTarefa PT WHERE PT.pessoa.id = :paramPessoaId AND PT.tarefa.id = :paramTarefaId")
    Optional<PessoaTarefa> findByPessoaAndDepartamentoId(
            @Param("paramPessoaId") Long pessoaId,
            @Param("paramTarefaId")Long tarefaId);

}
