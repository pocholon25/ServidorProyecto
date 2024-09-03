package com.idat.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VentaPedidoDTO {

	private Long idVenta;
    private Cliente cliente;
    private String fecha;
    private double importe;
    private List<DetalleVenta> detalleVenta;
    private String estadoPedido;
	
}
