package by.pankov.hes_test_demo.model.dto;

import by.pankov.hes_test_demo.annotation.UniqueUserName;
import by.pankov.hes_test_demo.model.Role;
import by.pankov.hes_test_demo.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserAccountDto {

    @Size(min = 3, max = 16)
    @UniqueUserName
    private String userName;

    @Size(min = 3, max = 16)
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-zA-Z])(?!.*\\s).*$", message = "Must be at least one digit, one letter, Latin only.")
    private String password;

    @Size(min = 1, max = 16)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only Latin letters are allowed")
    private String firstName;

    @Size(min = 1, max = 16)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only Latin letters are allowed")
    private String lastName;

    private Status status;

    private Role role;
}
