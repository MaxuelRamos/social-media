package ramos.maxuel.socialmedia.validator;

import ramos.maxuel.socialmedia.exception.BusinessException;
import ramos.maxuel.socialmedia.exception.Error;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Validator<T> {

    default void validate(T t) {
        List<Error> errors = getValidations().parallelStream()
                .map(f -> f.apply(t))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        handleErrors(errors);
    }

    List<Function<T, Optional<Error>>> getValidations();

    default void handleErrors(List<Error> errors) {
        if (!errors.isEmpty()) {
            throw new BusinessException(errors);
        }
    }

}
