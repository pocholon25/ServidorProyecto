package com.idat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.idat.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
}