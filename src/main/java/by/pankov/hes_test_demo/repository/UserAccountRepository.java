package by.pankov.hes_test_demo.repository;

import by.pankov.hes_test_demo.model.Status;
import by.pankov.hes_test_demo.model.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByUserName(String name);

    UserAccount findByUserNameAndStatus(String name, Status status);
}
