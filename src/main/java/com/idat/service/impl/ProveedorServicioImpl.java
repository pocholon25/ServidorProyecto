package com.idat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.idat.model.Proveedor;
import com.idat.repository.ProveedorRepository;
import com.idat.service.ProveedorService;

@Service
public class ProveedorServicioImpl implements ProveedorService{
	
	@Autowired
	private ProveedorRepository proveedorRepositorio;

	@Override
	public Proveedor saveProveedor(Proveedor proveedor) {
		return proveedorRepositorio.save(proveedor);
	}

	@Override
	public List<Proveedor> getAllProveedor() {
		return proveedorRepositorio.findAll();
	}

	@Override
	public Boolean deleteProveedor(Integer id) {
		Proveedor proveedor = proveedorRepositorio.findById(id).orElse(null);

		if (!ObjectUtils.isEmpty(proveedor)) {
			proveedorRepositorio.delete(proveedor);
			return true;
		}
		return false;
	}

	@Override
	public Proveedor getProveedorById(Integer id) {
		Proveedor proveedor = proveedorRepositorio.findById(id).orElse(null);
		return proveedor;
	}

	@Override
	public Proveedor updateProveedor(Proveedor proveedor) {
		// TODO Auto-generated method stub
		return null;
	}
}
