package com.idat.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.idat.model.Producto;
import com.idat.model.ProductUpdate;

public interface ProductService {

	Producto actualizarProducto(Integer id, ProductUpdate productUpdate, MultipartFile file);
	
	public Producto saveProducto(Producto producto);

	public List<Producto> getAllProductos();

	public Boolean deleteProducto(Integer id);

	public Producto getProductoById(Integer id);

	public Producto updateProducto(Producto producto, MultipartFile file);
	
	public List<Producto>getProductoByCategoria(String categoria);
	
	Page<Producto>getProductoPaginados(Pageable pageable);

}
