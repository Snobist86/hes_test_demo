package by.pankov.hes_test_demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping({"/login", ""})
    public String showLoginPage(Model model){
        model.addAttribute("title", "Login");
        return "Login";
    }

    @PostMapping("/login")
    public String loginUser(Model model) {
        model.addAttribute("title", "Login");
        return "Login";
    }

    @GetMapping("/logout")
    public String takeLogout(){
        return "Login";
    }

    @GetMapping("/403")
    public String accessDenied(Model model) {
            model.addAttribute("message", "You do not have access rights.");
        return "403Page";
    }
}
