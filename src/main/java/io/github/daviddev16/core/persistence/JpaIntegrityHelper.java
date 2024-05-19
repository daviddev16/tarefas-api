package io.github.daviddev16.core.persistence;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Classe auxiliar para tratar exceções de violação de constraint únicas.
 */
public class JpaIntegrityHelper {

    public static <T> T saveSafely(JpaRepository<T, ?> repository, T entity,
                                   UniqueViolationHandler<T> uniqueViolationHandler) {
        try {
            return repository.save(entity);
        } catch (DataIntegrityViolationException violationException) {
            handleViolationException(uniqueViolationHandler, violationException, entity);
        }
        return entity;
    }

    private static <T> void handleViolationException(
            UniqueViolationHandler<T> uniqueViolationHandler,
            DataIntegrityViolationException violationException, T entity) {

        Throwable causa = violationException.getCause();

        /*
         * Se é uma violação na integridade de dados e a causa não é uma violação
         * de constraint deverá lançar a exceção novamente por não ser uma causa
         * mapeada.
         **/
        if (!(causa instanceof ConstraintViolationException))
            throw violationException;

        String nomeConstraint = ((ConstraintViolationException)causa).getConstraintName();
        uniqueViolationHandler.handleConstraintViolation(nomeConstraint, entity);
    }

}
