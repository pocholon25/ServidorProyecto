package com.idat.service;

import com.idat.model.LoginRequest;

public interface LoginService {
	LoginRequest buscarPorEmailYPassword(String email, String password);
	LoginRequest guardarUsuario(LoginRequest login);
}
