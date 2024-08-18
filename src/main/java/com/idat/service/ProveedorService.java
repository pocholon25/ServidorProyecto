package com.idat.service;

import java.util.List;

import com.idat.model.Proveedor;

public interface ProveedorService {
	
	public Proveedor saveProveedor(Proveedor proveedor);

	public List<Proveedor> getAllProveedor();

	public Boolean deleteProveedor(Integer id);

	public Proveedor getProveedorById(Integer id);

	public Proveedor updateProveedor(Proveedor proveedor);

}
