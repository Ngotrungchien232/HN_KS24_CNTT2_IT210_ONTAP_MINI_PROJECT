package com.mini_project.controller;



import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {

    //  Hiển thị trang nhập tên
    @GetMapping("/welcome")
    public String showWelcome() {
        return "welcome"; // → templates/welcome.html
    }

    //  Nhận tên → lưu vào Session → redirect về trang todo
    @PostMapping("/welcome")
    public String saveOwner(@RequestParam String ownerName,
                            HttpSession session,
                            Model model) {
        // Validation: không cho nhập tên trống
        if (ownerName == null || ownerName.trim().isEmpty()) {
            model.addAttribute("error", "Tên không được để trống!");
            return "welcome";
        }
        session.setAttribute("ownerName", ownerName.trim());
        return "redirect:/";
    }
}