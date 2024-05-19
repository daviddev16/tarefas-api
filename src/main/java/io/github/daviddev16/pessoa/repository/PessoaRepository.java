package io.github.daviddev16.pessoa.repository;

import io.github.daviddev16.core.ServicoException;
import io.github.daviddev16.core.persistence.JpaSafeRepository;
import io.github.daviddev16.pessoa.Pessoa;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public @Repository interface PessoaRepository extends
        JpaSafeRepository<Pessoa, Long>, JpaSpecificationExecutor<Pessoa> {

    @Query(
            nativeQuery = true,
            value =
                """
                    SELECT
                        P.*
                    FROM
                        tb_tarefa_departamento TD
                    INNER JOIN
                        tb_pessoa_departamento PD
                        ON (TD.iddepartamento = PD.iddepartamento)
                    LEFT JOIN
                        tb_pessoa_tarefa PT
                        ON (PD.idpessoa = PT.idpessoa)
                    LEFT JOIN
                        tb_pessoa P
                        ON (PD.idpessoa = P.idpessoa)
                    WHERE
                        TD.idtarefa = :paramTarefaId
                        AND PT.idpessoa IS NULL
                    LIMIT 1;
                """
    )
    Optional<Pessoa> encontrarPessoaDisponivelParaTarefa(@Param("paramTarefaId") Long tarefaId);

    Optional<Pessoa> findByNome(String nome);

    @Override
    default void handleConstraintViolation(String constraintName, Pessoa pessoa) {
        if (constraintName.equals(Pessoa.NMPESSOA_CONSTRAINT_NAME))
            throw new ServicoException("O nome \"%s\" já está em uso.", pessoa.getNome());
    }

}
