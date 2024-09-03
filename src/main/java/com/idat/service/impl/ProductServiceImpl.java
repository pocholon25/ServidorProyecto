package com.idat.service.impl;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import com.idat.model.ProductUpdate;
import com.idat.model.Producto;
import com.idat.repository.ProductRepository;
import com.idat.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Producto saveProducto(Producto producto) {
		return productRepository.save(producto);
	}

	@Override
	public List<Producto> getAllProductos() {
		return productRepository.findAll();
	}

	@Override
	public Boolean deleteProducto(Integer id) {
		Producto product = productRepository.findById(id).orElse(null);

		if (!ObjectUtils.isEmpty(product)) {
			productRepository.delete(product);
			return true;
		}
		return false;
	}

	@Override
	public Producto getProductoById(Integer id) {
		Producto product = productRepository.findById(id).orElse(null);
		return product;
	}

	@Override
	public Producto updateProducto(Producto producto, MultipartFile file) {
		Producto dbProduct = getProductoById(producto.getId());

		String imageName = file.isEmpty() ? dbProduct.getImage() : file.getOriginalFilename();

		dbProduct.setNombre(producto.getNombre());
		dbProduct.setDescripcion(producto.getDescripcion());
		dbProduct.setCategoria(producto.getCategoria());
		dbProduct.setPrecio(producto.getPrecio());
		dbProduct.setStock(producto.getStock());
		dbProduct.setImage(imageName);
		

		Producto updateProduct = productRepository.save(dbProduct);

		if (!ObjectUtils.isEmpty(updateProduct)) {

			if (!file.isEmpty()) {

				try {
					File saveFile = new ClassPathResource("static/img").getFile();

					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
							+ file.getOriginalFilename());
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return producto;
		}
		return null;
	}

	@Override
	public List<Producto> getProductoByCategoria(String categoria) {
		return productRepository.findByCategoria(categoria);
	}

	@Override
	public Producto actualizarProducto(Integer id, ProductUpdate productUpdate, MultipartFile file) {
		Optional<Producto> productOpt = productRepository.findById(id);

		String imageName = file.isEmpty() ? productUpdate.getImage() : file.getOriginalFilename();
        if (productOpt.isPresent()) {
        	Producto producto = productOpt.get();
        	producto.setNombre(productUpdate.getNombre());
        	producto.setDescripcion(productUpdate.getDescripcion());
        	producto.setCategoria(productUpdate.getCategoria());
        	producto.setPrecio(productUpdate.getPrecio());
        	producto.setStock(productUpdate.getStock());
        	producto.setImage(imageName);
            return productRepository.save(producto);
        }
        return null;
	}

	@Override
	public Page<Producto> getProductoPaginados(Pageable pageable) {
	    // Verifica que el pageable tenga los parámetros correctos
	    logger.info("Página solicitada: " + pageable.getPageNumber());
	    logger.info("Tamaño de página: " + pageable.getPageSize());
	    
	    // Consulta al repositorio para obtener los productos paginados
	    Page<Producto> paginados = productRepository.findAll(pageable);

	    // Log para verificar el total de productos y páginas
	    logger.info("Total de productos en todas las páginas: " + paginados.getTotalElements());
	    logger.info("Total de páginas disponibles: " + paginados.getTotalPages());
	    logger.info("Productos en esta página: " + paginados.getContent().size());

	    // Retorna los productos paginados
	    return paginados;
	}
	
}
