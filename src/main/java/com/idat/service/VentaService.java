package com.idat.service;

import java.util.List;

import com.idat.model.Venta;

public interface VentaService extends ICRUD<Venta>{
    List<Venta> listarVentas();
}
