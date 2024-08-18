package com.idat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.idat.service.ClienteService;
import com.idat.service.LoginService;

import org.springframework.ui.Model;

import com.idat.model.LoginMessage;
import com.idat.model.LoginRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ClienteService service;

	@GetMapping("/")
	public String index() {
		System.out.println("Intento de login con email: ");
		return null;
	}
	
	@PostMapping("/saveLogin")
    public String saveLogin(@RequestParam String email, @RequestParam String password, Model model) {
		System.out.println("Intento de login con email: " + email);

        LoginRequest usuario = loginService.buscarPorEmailYPassword(email, password);
		System.out.println(usuario);

        if (usuario != null) {
            // Inicio de sesión exitoso
            model.addAttribute("LoginRequest", usuario);
            return "redirect:/admin/"; // Redirige a la página de inicio o dashboard
        } else {
            // Inicio de sesión fallido
            model.addAttribute("error", "Credenciales incorrectas");
            return "redirect:/login"; // Muestra de nuevo la página de login
        }
        //return "redirect:/admin/"; // Redirige a la página de inicio o dashboard
    }
}
