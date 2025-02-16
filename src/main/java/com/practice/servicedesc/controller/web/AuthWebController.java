package com.practice.servicedesc.controller.web;

import com.practice.servicedesc.security.JwtEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/web")
public class AuthWebController {

    @GetMapping("/login")
    public String loginPage() {
        return "main/login";
    }

    @GetMapping("/home")
    public String homePage(@AuthenticationPrincipal JwtEntity details, Model model) {
        String role = new ArrayList<GrantedAuthority>(details.getAuthorities()).get(0).getAuthority();
        model.addAttribute("role", role);
        return "main/home";
    }

}
