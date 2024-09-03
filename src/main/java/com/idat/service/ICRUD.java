package com.idat.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.idat.model.Usuario;
import com.idat.model.UsuarioUpdate;

public interface ICRUD<T> {

	T registrar(T t);
	T modificar(T t);
	boolean eliminar(Long id);
	T buscar(Long id);
	List<T> listar();
	Page<T> listarPagina(Pageable pagina);
	Usuario actualizarUsuario(Long id, UsuarioUpdate usuarioUpdate);
}
