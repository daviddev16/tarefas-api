package io.github.daviddev16.core.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * JpaSafeRepository permite com que as interfaces/classes filhas consigam
 * lidar com exceções de violação de constraint única com mais facilidade.
 * Essa interface nada mais é que uma extensão para {@link JpaRepository}.
 *
 * {@link UniqueViolationHandler#handleConstraintViolation(String, Object)}
 *
 **/
@NoRepositoryBean
public interface JpaSafeRepository<T, ID> extends JpaRepository<T, ID>, UniqueViolationHandler<T> {

    default T saveSafely(T entity) {
        return JpaIntegrityHelper.saveSafely(this, entity, this);
    }

}
