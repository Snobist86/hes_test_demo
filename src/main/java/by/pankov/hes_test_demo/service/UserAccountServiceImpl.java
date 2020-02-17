package by.pankov.hes_test_demo.service;

import by.pankov.hes_test_demo.exception.ResourceNotFoundException;
import by.pankov.hes_test_demo.model.Role;
import by.pankov.hes_test_demo.model.Status;
import by.pankov.hes_test_demo.model.dto.CreateUserAccountDto;
import by.pankov.hes_test_demo.model.dto.UpdateUserAccountDto;
import by.pankov.hes_test_demo.model.entity.UserAccount;
import by.pankov.hes_test_demo.repository.UserAccountRepository;
import by.pankov.hes_test_demo.service.impl.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private static final String ROLE_PREFIX = "ROLE_";

    private final UserAccountRepository accountRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    private void createFirstUser(){
        UserAccount userAccount = UserAccount.builder()
                .userName("first_user")
                .password(bCryptPasswordEncoder.encode("123qwerty"))
                .firstName("first")
                .lastName("user")
                .status(Status.ACTIVE)
                .role(Role.ADMIN)
                .createdAt(LocalDateTime.now())
                .build();
        save(userAccount);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount account = accountRepository.findByUserNameAndStatus(username, Status.ACTIVE);
        if (account == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + account.getRole()));

        return new org.springframework.security.core.userdetails.User(account.getUserName(), account.getPassword(), authorities);
    }

    @Override
    public void save(UserAccount user) {
        accountRepository.save(user);
    }

    @Override
    public void saveNewUserAccount(CreateUserAccountDto userDto) {
        UserAccount userAccount = UserAccount.builder()
                .userName(userDto.getUserName())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .status(userDto.getStatus())
                .role(userDto.getRole())
                .createdAt(LocalDateTime.now())
                .build();
        save(userAccount);
    }

    @Override
    public void updateUserAccount(UpdateUserAccountDto userAccountDto) {
        Optional<UserAccount> userAccountInDb = accountRepository.findById(userAccountDto.getId());
        if (userAccountInDb.isPresent()){
            userAccountInDb.get().setFirstName(userAccountDto.getFirstName());
            userAccountInDb.get().setLastName(userAccountDto.getLastName());
            userAccountInDb.get().setRole(userAccountDto.getRole());
            userAccountInDb.get().setStatus(userAccountDto.getStatus());
        }
        save(userAccountInDb.get());
    }

    @Override
    public void changeStatus(Long id, Status status) {
        Optional<UserAccount> userAccountInDb = accountRepository.findById(Long.valueOf(id));
        if (userAccountInDb.isPresent()){
            userAccountInDb.get().setStatus(status);
            save(userAccountInDb.get());
        } else {
            throw new ResourceNotFoundException("User not found!");
        }
    }


    @Override
    public UserAccount findById(String id) {
        Optional<UserAccount> userAccount = accountRepository.findById(Long.valueOf(id));
        if (userAccount.isPresent()){
            return userAccount.get();
        } else {
            throw new ResourceNotFoundException("User not found!");
        }
    }

    @Override
    public UserAccount findByUserName(String name) {
        return Optional.of(name)
                       .map(accountRepository::findByUserName)
                       .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public List<UserAccount> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Page<UserAccount> findAllPagination(Pageable pageable){
        return accountRepository.findAll(pageable);
    }
}
