package com.idat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.idat.service.LoginService;
import org.springframework.ui.Model;
import com.idat.model.LoginRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@GetMapping("/")
	public String index() {
		return null;
	}
	
	@PostMapping("/saveLogin")
    public String saveLogin(@RequestParam String email, @RequestParam String password, Model model) {

        LoginRequest usuario = loginService.buscarPorEmailYPassword(email, password);

        if (usuario != null) {
            model.addAttribute("LoginRequest", usuario);
            return "redirect:/admin/"; 
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "redirect:/login"; 
        }
    }
}
