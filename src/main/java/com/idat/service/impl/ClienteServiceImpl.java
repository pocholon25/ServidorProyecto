package com.idat.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.idat.model.Cliente;
import com.idat.model.ClienteUpdate;
import com.idat.model.LoginMessage;
import com.idat.model.LoginRequest;
import com.idat.model.LoginRequestDto;
import com.idat.model.Usuario;
import com.idat.model.UsuarioUpdate;
import com.idat.repository.IClienteRepository;
import com.idat.service.ClienteService;


@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private IClienteRepository dao;
	
	@Override
	public Cliente registrar(Cliente t) {
		t.setIdcliente(0L);
		return dao.save(t);
	}

	@Override
	public Cliente modificar(Cliente t) {
		if (dao.existsById(t.getIdcliente())) {
            return dao.save(t);
        }
        return null;
	}

	@Override
	public boolean eliminar(Long id) {
		Optional<Cliente> opt=dao.findById(id);
		if (opt!=null)
		{
			dao.delete(opt.get());
			return true;
		}
		return false;
	}

	@Override
	public Cliente buscar(Long id) {
		return dao.findById(id).get();
	}

	@Override
	public List<Cliente> listar() {
		return dao.findAll();
	}

	@Override
	public Page<Cliente> listarPagina(Pageable pagina) {
		return dao.findAll(pagina);
	}

	@Override
	public LoginMessage loginUsuario(LoginRequestDto loginRequestDto) {
		 Optional<Cliente> clienteOpt = dao.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
	        if (clienteOpt.isPresent()) {
	            Cliente cliente = clienteOpt.get();
	            return new LoginMessage(
	                "Login successful",
	                true,
	                cliente.getNombre(),
	                cliente.getEmail(),
	                cliente.getCelular(),
	                cliente.getPassword(),
	                cliente.getIdcliente().intValue()
	            );
	        } else {
	            return new LoginMessage("Credenciales Incorrectas !!!", false);
	        }
	}

	@Override
	public Cliente obtenerClientePorId(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public Cliente actualizarCliente(Long id, ClienteUpdate clienteUpdate) {
		Optional<Cliente> clienteOpt = dao.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setNombre(clienteUpdate.getNombre());
            cliente.setEmail(clienteUpdate.getEmail());
            cliente.setCelular(clienteUpdate.getCelular());
            cliente.setPassword(clienteUpdate.getPassword());
            return dao.save(cliente);
        }
        return null;
    }

	@Override
	public List<Cliente> getAllCliente() {
		return dao.findAll();
	}

	@Override
	public Boolean deleteCliente(Long id) {
		Cliente cliente = dao.findById(id).orElse(null);
		
		if(!ObjectUtils.isEmpty(cliente)) {
			dao.delete(cliente);
			return true;
		}
		return false;
	}

	@Override
	public Usuario actualizarUsuario(Long id, UsuarioUpdate usuarioUpdate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente saveCliente(Cliente cliente) {
		return dao.save(cliente);
	}
	}

