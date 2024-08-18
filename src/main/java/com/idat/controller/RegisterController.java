package com.idat.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.idat.model.Cliente;
import com.idat.service.ClienteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private ClienteService service;

	@GetMapping("/")
	public String index() {
		System.out.println("Intento de login con email: ");
		return null;
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute Cliente cliente,HttpSession session) throws IOException {
	
		Cliente saveCliente = service.registrar(cliente);
		
		if(ObjectUtils.isEmpty(saveCliente)) {
			session.setAttribute("errorMsg", "No se pudo guardar. Error interno del servidor");
		}else {
			session.setAttribute("successMsg", "Registro Satisfactorio");
		}
		return "redirect:/login";
	}

}
