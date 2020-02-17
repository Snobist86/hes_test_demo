package by.pankov.hes_test_demo.validator;

import by.pankov.hes_test_demo.annotation.UniqueUserName;
import by.pankov.hes_test_demo.model.entity.UserAccount;
import by.pankov.hes_test_demo.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {

    @Autowired
    UserAccountRepository accountRepository;

    @Override
    public void initialize(UniqueUserName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        UserAccount userAccount = accountRepository.findByUserName(value);
        return userAccount == null;
    }

}
