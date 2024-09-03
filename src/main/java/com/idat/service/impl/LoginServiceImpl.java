package com.idat.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idat.model.LoginRequest;
import com.idat.repository.LoginRepository;
import com.idat.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private LoginRepository usuarioRepository;

	@Override
	public LoginRequest buscarPorEmailYPassword(String email, String password) {
		Optional<LoginRequest> usuario = usuarioRepository.findByEmail(email);
	    
	    if (usuario.isPresent()) {
	        System.out.println("Usuario encontrado en la base de datos.");
	        if (usuario.get().getPassword().equals(password)) {
	            System.out.println("Contraseña válida.");
	            return usuario.get();
	        } else {
	            System.out.println("Contraseña inválida.");
	        }
	    } else {
	        System.out.println("Usuario no encontrado.");
	    }
	    return null;
	}

	@Override
	public LoginRequest guardarUsuario(LoginRequest login) {
		return usuarioRepository.save(login);
	}

}
