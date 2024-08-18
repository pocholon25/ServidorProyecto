package com.idat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.idat.model.Venta;

@Repository
public interface VentaRepository extends CrudRepository<Venta, Long> {
}
