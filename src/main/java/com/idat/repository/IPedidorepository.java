package com.idat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.model.Pedido;

public interface IPedidorepository extends JpaRepository<Pedido, Long> {    
	// Obtener pedidos por el ID del cliente
    List<Pedido> findByClienteIdcliente(Long idcliente);

    // Obtener un pedido por el ID de la venta
    Pedido findByVentaIdVenta(Long idVenta);
}