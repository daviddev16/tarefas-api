package io.github.daviddev16.core.persistence;

public interface UniqueViolationHandler<T> {

    void handleConstraintViolation(String constraintName, T entity);

}
