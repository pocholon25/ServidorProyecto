package com.idat.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idat.model.Pedido;
import com.idat.model.Venta;
import com.idat.repository.IPedidorepository;
import com.idat.repository.VentaDao;

@Service
public class PedidoService {

	@Autowired
    private IPedidorepository pedidorepository;
	
	@Autowired
	private VentaDao ventaDao;
	
	public List<Pedido> obtenerTodosLosPedidos() {
        return pedidorepository.findAll();
    }

    // Obtener pedidos por ID de cliente
    public List<Pedido> obtenerPedidosPorIdCliente(Long idCliente) {
        return pedidorepository.findByClienteIdcliente(idCliente);
    }

    // Crear un nuevo pedido con una venta asociada
 // Crear un nuevo pedido con una venta asociada
    public Pedido crearPedido(Long idVenta) {
        // Primero, obtenemos la venta asociada por su ID
        Venta venta = ventaDao.findById(idVenta)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + idVenta));

        // Creamos el nuevo pedido
        Pedido pedido = new Pedido();
        pedido.setVenta(venta);
        pedido.setCliente(venta.getCliente()); // Asociar cliente de la venta al pedido
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstadoPedido("PENDIENTE");

        return pedidorepository.save(pedido);
    }


    // Actualizar estado de un pedido
    public Pedido actualizarEstadoPedido(Long idPedido, String nuevoEstado) {
        Pedido pedido = pedidorepository.findById(idPedido)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + idPedido));

        pedido.setEstadoPedido(nuevoEstado);
        return pedidorepository.save(pedido);
        
    }

    public Pedido obtenerPedidoPorIdVenta(Long idVenta) {
        return pedidorepository.findByVentaIdVenta(idVenta);
    }
}
