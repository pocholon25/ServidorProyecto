package com.idat.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.idat.model.Venta;
import com.idat.model.VentaPedidoDTO;

public interface VentaService{
	
	VentaPedidoDTO registrar(Venta t);
    Venta modificar(Venta t);
    boolean eliminar(Long id);
    Venta buscar(Long id);
    List<Venta> listar();
    Page<Venta> listarPagina(Pageable pagina);

}

