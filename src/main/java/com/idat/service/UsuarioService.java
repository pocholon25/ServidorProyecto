package com.idat.service;

import java.util.List;
import com.idat.model.Usuario;
import com.idat.model.UsuarioUpdate;
import com.idat.model.LoginMessage;
import com.idat.model.LoginRequest;

public interface UsuarioService extends ICRUD<Usuario>{
	
	LoginMessage loginUsuario(LoginRequest loginRequest);
	
	Usuario obtenerUsuarioPorId(Long id);

	Usuario actualizarUsuario(Long id, UsuarioUpdate usuarioUpdate);
	
	public Usuario saveUsuario(Usuario usuario);
	
	public List<Usuario> getAllUsuario();
	
	public Boolean deleteUsuario(Long id);

	public Usuario updateUsuario(Usuario usuario);

	public Usuario getUsuarioById(Long id);
}
