package com.idat.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
}