package by.pankov.hes_test_demo.service.impl;

import by.pankov.hes_test_demo.model.Status;
import by.pankov.hes_test_demo.model.dto.CreateUserAccountDto;
import by.pankov.hes_test_demo.model.dto.UpdateUserAccountDto;
import by.pankov.hes_test_demo.model.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserAccountService extends UserDetailsService {

    void save(UserAccount userAccount);

    void saveNewUserAccount(CreateUserAccountDto userAccountDto);

    void updateUserAccount(UpdateUserAccountDto userAccountDto);

    void changeStatus(Long id, Status status);

    UserAccount findByUserName(String name);

    UserAccount findById(String id);

    List<UserAccount> findAll();
}
