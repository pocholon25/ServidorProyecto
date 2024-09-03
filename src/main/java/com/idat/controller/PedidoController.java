package com.idat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idat.model.EstadoPedidoRequest;
import com.idat.model.Pedido;
import com.idat.service.impl.PedidoService;


@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Endpoint para crear un nuevo pedido con una venta
    @PostMapping("/venta/{idVenta}")
    public Pedido crearPedido(@PathVariable Long idVenta) {
        return pedidoService.crearPedido(idVenta);
    }

    // Endpoint para actualizar el estado de un pedido
    @PutMapping("/{idPedido}/estado")
    public Pedido actualizarEstadoPedido(@PathVariable Long idPedido, @RequestBody EstadoPedidoRequest request) {
        return pedidoService.actualizarEstadoPedido(idPedido, request.getEstadoPedido());
    }

    // Endpoint para obtener un pedido por ID de venta
    @GetMapping("/venta/{idVenta}")
    public Pedido obtenerPedidoPorIdVenta(@PathVariable Long idVenta) {
        return pedidoService.obtenerPedidoPorIdVenta(idVenta);
    }
    @GetMapping("/cliente/{idCliente}")
    public List<Pedido> obtenerPedidosPorIdCliente(@PathVariable Long idCliente) {
        return pedidoService.obtenerPedidosPorIdCliente(idCliente);
    }
}