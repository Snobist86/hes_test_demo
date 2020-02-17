package by.pankov.hes_test_demo.controller;

import by.pankov.hes_test_demo.model.Role;
import by.pankov.hes_test_demo.model.Status;
import by.pankov.hes_test_demo.model.dto.CreateUserAccountDto;
import by.pankov.hes_test_demo.model.dto.UpdateUserAccountDto;
import by.pankov.hes_test_demo.model.entity.UserAccount;
import by.pankov.hes_test_demo.service.impl.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserAccountService accountService;

    @ModelAttribute("userAccount")
    public CreateUserAccountDto createUserAccountDto(){
        return new CreateUserAccountDto();
    }

    @ModelAttribute("editUserAccount")
    public UpdateUserAccountDto userAccountDto(){
        return new UpdateUserAccountDto();
    }

    @ModelAttribute("allRoles")
    public Role[] allRoles(){
        return Role.values();
    }

    @ModelAttribute("allStatuses")
    public Status[] allStatuses(){
        return Status.values();
    }

    @GetMapping
    public String showListPage(Model model,
                               @PageableDefault(size = 5) Pageable pageable) {

        Page<UserAccount> accountPage = accountService.findAllPagination(pageable);
        model.addAttribute("accountPage", accountPage);

        return "List";
    }

    @GetMapping("/{id}")
    public String getUserAccountView(@PathVariable String id, Model model){
        UserAccount userAccount = accountService.findById(id);
        model.addAttribute("account", userAccount);
        model.addAttribute("active", Status.ACTIVE);
        model.addAttribute("inactive", Status.INACTIVE);
        return "View";
    }

    @PostMapping("/lock")
    public String lockAccount(
            @RequestParam("id") String id,
            @RequestParam("status") Status status){
        accountService.changeStatus(Long.valueOf(id), status);
        return "redirect:/user/" + id + "?success";
    }

    @GetMapping("/new")
    public String showRegistrationPage(){
        return "New";
    }

    @PostMapping("/new")
    public String createNewUserAccount(
            @ModelAttribute("userAccount") @Valid CreateUserAccountDto accountDto,
            BindingResult result){

        if (result.hasErrors()){
            return "New";
        } else {
            accountService.saveNewUserAccount(accountDto);
            return "redirect:/user/new?success";
        }
    }

    @PostMapping("/{id}/edit")
    public String editUserAccount(
            @PathVariable("id") String id,
            @ModelAttribute("editUserAccount") @Valid UpdateUserAccountDto accountDto,
            BindingResult result,
            Model model){
        UserAccount userAccount = accountService.findById(id);
        model.addAttribute("defaultAccount", userAccount);
        if (result.hasErrors()){
            return "Edit";
        } else {
            accountService.updateUserAccount(accountDto);
            return "redirect:/user?changeSuccess";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable("id") String id, Model model){
        UserAccount userAccount = accountService.findById(id);
        model.addAttribute("defaultAccount", userAccount);
        return "Edit";
    }
}
