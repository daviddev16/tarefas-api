package io.github.daviddev16.departamento.repository;

import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.core.ServicoException;
import io.github.daviddev16.core.persistence.JpaSafeRepository;
import io.github.daviddev16.departamento.projection.DepartamentoSumarioProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public @Repository interface DepartamentoRepository extends JpaSafeRepository<Departamento, Long> {

    Optional<Departamento> findByNome(String nome);

    @Query("FROM Departamento D WHERE D.nome IN :paramDepartamentos")
    List<Departamento> findAllByNomes(@Param("paramDepartamentos") Set<String> departamentos);

    @Query(
            nativeQuery = true,
            value =
                    """
                    SELECT
                        DE.iddepartamento AS "id",
                        DE.nmdepartamento AS "nome",
                        COUNT(DISTINCT PDE.idpessoa) AS "quantidadeDePessoas",
                        COUNT(DISTINCT TDE.idtarefa) AS "quantidadeDeTarefas"
                    FROM
                        tb_departamento DE
                    LEFT JOIN
                        tb_pessoa_departamento PDE
                        ON DE.iddepartamento = PDE.iddepartamento
                    LEFT JOIN
                        tb_tarefa_departamento TDE
                        ON DE.iddepartamento = TDE.iddepartamento
                    GROUP BY
                        DE.iddepartamento
                    """
    )
    List<DepartamentoSumarioProjection> obterSumarioDepartamentos();

    @Override
    default void handleConstraintViolation(String constraintName, Departamento departamento) {
        if (constraintName.equals(Departamento.NMDEPARTAMENTO_CONSTRAINT_NAME))
            throw new ServicoException("Um departamento com o nome \"%s\" já existe.", departamento.getNome());
    }
}
