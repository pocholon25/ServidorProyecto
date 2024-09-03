package com.idat.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.idat.model.Venta;

@Repository
public interface VentaRepository extends CrudRepository<Venta, Long> {
}
