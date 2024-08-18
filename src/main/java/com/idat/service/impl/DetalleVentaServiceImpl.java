package com.idat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idat.model.DetalleVenta;
import com.idat.repository.DetalleVentaRepository;
import com.idat.service.DetalleVentaService;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {
	
	@Autowired
	private DetalleVentaRepository repository;

	@Override
	public List<DetalleVenta> getAllDetalle() {
		return repository.findAll();
	}

}
