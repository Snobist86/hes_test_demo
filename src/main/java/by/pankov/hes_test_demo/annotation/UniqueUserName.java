package by.pankov.hes_test_demo.annotation;

import by.pankov.hes_test_demo.validator.UniqueUserNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueUserNameValidator.class})
@Documented
public @interface UniqueUserName {

    String message() default "This name is already in use.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
