package com.idat.service;

import java.util.List;
import com.idat.model.Cliente;
import com.idat.model.ClienteUpdate;
import com.idat.model.LoginMessage;
import com.idat.model.LoginRequestDto;

public interface ClienteService extends ICRUD<Cliente>{
	
	LoginMessage loginUsuario(LoginRequestDto loginRequestDto);
	
	Cliente obtenerClientePorId(Long id);

    Cliente actualizarCliente(Long id, ClienteUpdate clienteUpdate);
	
	public List<Cliente> getAllCliente();
	
	public Boolean deleteCliente(Long id);
	
	public Cliente saveCliente(Cliente cliente);

}
