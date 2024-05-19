package io.github.daviddev16.pessoa.specification;

import io.github.daviddev16.core.Utilitarios;
import io.github.daviddev16.pessoa.Pessoa;
import io.github.daviddev16.pessoa.dto.OpcoesBuscaPessoaDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class PessoaSpecification {

    public static Specification<Pessoa> buscarPessoaPorEspecificacao(OpcoesBuscaPessoaDTO opcoesBuscaPessoaDTO) {
        return (root, query, criteriaBuilder) ->
        {
            String nome = opcoesBuscaPessoaDTO.getNome();
            LocalDate dataInicial = opcoesBuscaPessoaDTO.getDataCriacaoInicial();
            LocalDate dataFinal = opcoesBuscaPessoaDTO.getDataCriacaoFinal();

            List<Predicate> predicates = new ArrayList<>(3);

            if (!Utilitarios.isNullOrBlank(nome))
                predicates.add(criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));

            if (validarIntervaloDatas(dataInicial, dataFinal))
                predicates.add(criteriaBuilder.between(root.get("dataCriacao"), dataInicial, dataFinal));

            if (predicates.isEmpty())
                return criteriaBuilder.conjunction();

            return criteriaBuilder.or(predicates.toArray(Predicate[]::new));
        };
    }

    private static boolean validarIntervaloDatas(LocalDate dataInicial, LocalDate dataFinal) {
        return dataInicial != null && dataFinal != null && dataInicial.isBefore(dataFinal);
    }

}
