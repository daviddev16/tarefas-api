package io.github.daviddev16.pessoa.repository;

import io.github.daviddev16.pessoa.PessoaDepartamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public @Repository interface PessoaDepartamentoRepository extends JpaRepository<PessoaDepartamento, Long> {

    @Query("FROM PessoaDepartamento PD WHERE PD.pessoa.id = :paramPessoaId AND PD.departamento.id = :paramDepartamentoId")
    Optional<PessoaDepartamento> findByPessoaAndDepartamentoId(
            @Param("paramPessoaId") Long pessoaId,
            @Param("paramDepartamentoId")Long departamentoId);

}
