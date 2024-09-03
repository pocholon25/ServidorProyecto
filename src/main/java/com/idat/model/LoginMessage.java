package com.idat.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginMessage {
	
	
	private String message;
	private boolean success;
	private String nombre;
	private String email;
	private String celular;
	private String password;
	private Integer idcliente;
	public LoginMessage(String message, boolean success, String nombre, String email, String celular, String password,
			int idcliente) {
		super();
		this.message = message;
		this.success = success;
		this.nombre = nombre;
		this.email = email;
		this.celular = celular;
		this.password = password;
		this.idcliente = idcliente;
	}
	public LoginMessage(String message, boolean success) {
		super();
		this.message = message;
		this.success = success;
	}
	
	
	
}
	
	
	