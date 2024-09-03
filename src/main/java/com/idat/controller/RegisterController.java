package com.idat.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idat.model.Usuario;
import com.idat.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
    private UsuarioService usuarioService;

	@GetMapping("/")
	public String index() {
		System.out.println("Intento de login con email: ");
		return null;
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute Usuario usuario,HttpSession session) throws IOException {
		
		Usuario saveUsuario = usuarioService.saveUsuario(usuario);
	
		//Cliente saveCliente = service.saveCliente(cliente);
		
		if(ObjectUtils.isEmpty(saveUsuario)) {
			session.setAttribute("errorMsg", "No se pudo guardar. Error interno del servidor");
			return "redirect:/register";
		}else {
			session.setAttribute("successMsg", "Registro Satisfactorio");
			return "redirect:/login";
		}
	}

}
