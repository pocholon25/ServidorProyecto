package com.idat.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.idat.model.LoginMessage;
import com.idat.model.LoginRequest;
import com.idat.model.Usuario;
import com.idat.model.UsuarioUpdate;
import com.idat.repository.UsuarioRepository;
import com.idat.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository dao;

	@Override
	public Boolean deleteUsuario(Long id) {
		Usuario usuario = dao.findById(id).orElse(null);
		
		if(!ObjectUtils.isEmpty(usuario)) {
			dao.delete(usuario);
			return true;
		}
		return false;
	}

	@Override
	public Usuario registrar(Usuario t) {
		t.setIdusuario(0L);
		return dao.save(t);
	}

	@Override
	public Usuario modificar(Usuario t) {
		if (dao.existsById(t.getIdusuario())) {
            return dao.save(t);
        }
        return null;
	}

	@Override
	public boolean eliminar(Long id) {
		Optional<Usuario> opt=dao.findById(id);
		if (opt!=null)
		{
			dao.delete(opt.get());
			return true;
		}
		return false;
	}

	@Override
	public Usuario buscar(Long id) {
		return dao.findById(id).get();
	}

	@Override
	public List<Usuario> listar() {
		return dao.findAll();
	}

	@Override
	public Page<Usuario> listarPagina(Pageable pagina) {
		return dao.findAll(pagina);
	}


	@Override
	public LoginMessage loginUsuario(LoginRequest loginRequest) {
		 Optional<Usuario> usuarioOpt = dao.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
	        if (usuarioOpt.isPresent()) {
	        	Usuario usuario = usuarioOpt.get();
	            return new LoginMessage(
	                "Login successful",
	                true,
	                usuario.getNombre(),
	                usuario.getEmail(),
	                usuario.getCelular(),
	                usuario.getPassword(),
	                usuario.getIdusuario().intValue()
	            );
	        } else {
	            return new LoginMessage("Invalid credentials", false, null, null,null, null, 0);
	        }
	}

	@Override
	public Usuario obtenerUsuarioPorId(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public Usuario actualizarUsuario(Long id, UsuarioUpdate usuarioUpdate) {
		Optional<Usuario> usuarioOpt = dao.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setNombre(usuarioUpdate.getNombre());
            usuario.setEmail(usuarioUpdate.getEmail());
            usuario.setCelular(usuarioUpdate.getCelular());
            usuario.setPassword(usuarioUpdate.getPassword()); // Asegúrate de cifrar la contraseña antes de guardar
            return dao.save(usuario);
        }
        return null;
	}

	@Override
	public List<Usuario> getAllUsuario() {
		return dao.findAll();
	}

	
	@Override
	public Usuario saveUsuario(Usuario usuario) {
		return dao.save(usuario);
	}

	@Override
	public Usuario getUsuarioById(Long id) {
		Usuario usuario = dao.findById(id).orElse(null);
		return usuario;
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		Usuario dbUsuario = getUsuarioById(usuario.getIdusuario());

		dbUsuario.setNombre(usuario.getNombre());
		dbUsuario.setCelular(usuario.getCelular());
		dbUsuario.setEmail(usuario.getEmail());
		dbUsuario.setPassword(usuario.getPassword());
		

		Usuario updateUsuario = dao.save(dbUsuario);

		if (!ObjectUtils.isEmpty(updateUsuario)) {			
			return usuario;
		}
		return null;
	}

}
