package com.idat.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.idat.model.Categoria;
import com.idat.model.Producto;
import com.idat.model.Proveedor;
import com.idat.model.Cliente;
import com.idat.service.CategoriaServicio;
import com.idat.service.ClienteService;
import com.idat.service.DetalleVentaService;
import com.idat.service.ProductService;
import com.idat.service.ProveedorService;
import com.idat.service.VentaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private CategoriaServicio categoriaServicio;

	@Autowired
    private ProductService productService;
	
	@Autowired
    private ProveedorService proveedorService;
	
	@Autowired
    private ClienteService clienteService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private DetalleVentaService detalleService;


	@GetMapping("/")
	public String index() {
		return "admin/index";
	}


	@GetMapping("/categoria")
	public String categoria(Model model) {
		model.addAttribute("categorias",categoriaServicio.getAllCategoria());
		
		return "admin/categoria";
	}

	/*
	 * @PostMapping("/savecategoria") 
	 * public String saveCategoria(@ModelAttribute
	 * Categoria categoria, @RequestParam("file") MultipartFile file, HttpSession
	 * session) { try { if (categoria.getNombre() == null ||
	 * categoria.getNombre().isEmpty()) { session.setAttribute("errorMsg",
	 * "El nombre de la Categoría no puede estar vacío"); } else { String imageName
	 * = file != null ? file.getOriginalFilename() : "default.jpg";
	 * categoria.setImageName(imageName);
	 * 
	 * Boolean existCategoria =
	 * categoriaServicio.existsByNombre(categoria.getNombre());
	 
	 * if (existCategoria) { session.setAttribute("errorMsg",
	 * "El nombre de la Categoría ya existe");
	 * session.removeAttribute("successMsg"); // Eliminar mensaje de éxito si existe
	 * } else { Categoria saveCategoria =
	 * categoriaServicio.saveCategoria(categoria);
	 * 
	 * if (saveCategoria != null) { session.setAttribute("successMsg",
	 * "Registro Satisfactorio"); session.removeAttribute("errorMsg"); // Eliminar
	 * mensaje de error si existe } else { session.setAttribute("errorMsg",
	 * "No se pudo guardar. Error interno del servidor"); } } } } catch (Exception
	 * e) { session.setAttribute("errorMsg",
	 * "No se pudo guardar. Error interno del servidor"); }
	 * 
	 * return "redirect:/admin/categoria"; }
	 */
	@PostMapping("/savecategoria")
	public String saveCategoria(@ModelAttribute Categoria categoria, @RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
		String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
		categoria.setImageName(imageName);
		
		Boolean existCategoria = categoriaServicio.existsByNombre(categoria.getNombre());
		
		if(existCategoria) {
			session.setAttribute("errorMsg", "El nombre de la Categoría ya existe");
		}else {
			Categoria saveCategoria = categoriaServicio.saveCategoria(categoria);
			
			if(ObjectUtils.isEmpty(saveCategoria)) {
				session.setAttribute("errorMsg", "No se pudo guardar. Error interno del servidor");
			}else {
				
				File saveFile = new ClassPathResource("static/img").getFile();
				
				Path path =  Paths.get(saveFile.getAbsolutePath()+File.separator+ "categoria_img"+File.separator+file.getOriginalFilename());
				
				System.out.println(path);
				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				session.setAttribute("successMsg", "Registro Satisfactorio");

			}
		}
		return "redirect:/admin/categoria";		
		
	}
	
	@GetMapping("/deleteCategoria/{id}")
	public String deleteCategoria(@PathVariable Long id, HttpSession session) {
		Boolean deleteCategoria = categoriaServicio.deleteCategoria(id);
		
		if(deleteCategoria) {
			session.setAttribute("successMsg", "Categoria eliminada exitosamente");
		}else {
			session.setAttribute("errorMsg", "Error en el servidor");
		}
		return "redirect:/admin/categoria";
	}
	
	@GetMapping("/loadEditCategoria/{id}")
	public String loadEditCategoria(@PathVariable Long id, Model model) {
		model.addAttribute("categoria",categoriaServicio.getCategoriaById(id));
		
		return "admin/edit_categoria";
	}
	
	// PRODUCTOS SERVICES

	@GetMapping("/addproducto")
	public String addProducto(Model model) {
	    model.addAttribute("producto", new Producto()); // Agregar un objeto Producto vacío al modelo
	    model.addAttribute("categorias", categoriaServicio.getAllCategoria()); // Cargar todas las categorías
		model.addAttribute("productos",productService.getAllProductos()); // Listar Productos
	    return "admin/addproducto";
	}
	
	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute Producto product, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {
		
		//String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();		
		String imageName = file != null ? file.getOriginalFilename() : "default.jpg";

		product.setImage(imageName);
		
		Producto saveProduct = productService.saveProducto(product);
		
		if(ObjectUtils.isEmpty(saveProduct)) {
			session.setAttribute("errorMsg", "No se pudo guardar. Error interno del servidor");
		}else {
			
			File saveFile = new ClassPathResource("static/img").getFile();
			
			Path path =  Paths.get(saveFile.getAbsolutePath()+File.separator+ "product_img"+File.separator+file.getOriginalFilename());
			
			System.out.println(path);
			
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			session.setAttribute("successMsg", "Registro Satisfactorio");
		}
		return "redirect:/admin/addproducto";
	}
	
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable Integer id, HttpSession session) {
		Boolean deleteProduct = productService.deleteProducto(id);
		
		if(deleteProduct) {
			session.setAttribute("successMsg", "Producto eliminado exitosamente");
		}else {
			session.setAttribute("errorMsg", "Error en el servidor");
		}
		return "redirect:/admin/addproducto";
	}
	
	@GetMapping("/loadEditProduct/{id}")
	public String loadEditProduct(@PathVariable Integer id, Model model) {
		model.addAttribute("producto",productService.getProductoById(id));
	    model.addAttribute("categorias", categoriaServicio.getAllCategoria()); // Cargar todas las categorías
		
		return "admin/edit_product";
	}

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id, HttpSession session) {
        Boolean deleteProduct = productService.deleteProducto(id);
        if (deleteProduct) {
            session.setAttribute("succMsg", "Product deleted successfully");
        } else {
            session.setAttribute("errorMsg", "Something went wrong on server");
        }
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable int id, Model m) {
        m.addAttribute("product", productService.getProductoById(id));
        m.addAttribute("categories", categoriaServicio.getAllCategoria());
        return "admin/edit_product";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Producto product, 
                                @RequestParam("file") MultipartFile image,
                                HttpSession session) throws IOException {
        // Obtener el nombre del archivo o usar un nombre predeterminado
        String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();

        // Actualizar el nombre del archivo en el objeto Producto
        product.setImage(imageName);

        // Llamar al método updateProducto del servicio para actualizar el producto
        Producto updatedProduct = productService.updateProducto(product, image);

        // Verificar si el producto se actualizó correctamente
        if (updatedProduct != null) {
            // Establecer un mensaje de éxito en la sesión
            session.setAttribute("succMsg", "Producto actualizado exitosamente");
        } else {
            // Establecer un mensaje de error en la sesión
            session.setAttribute("errorMsg", "Algo salió mal en el servidor");
        }

        // Redirigir a la página de edición del producto actualizado
        return "redirect:/admin/edit/" + product.getId();
    }

    // PROVEEDORES SERVICES

	@GetMapping("/proveedor")	
	public String proveedor(Model model) {
	    model.addAttribute("proveedor", new Proveedor()); // Agregar un objeto Producto vacío al modelo
		model.addAttribute("proveedores",proveedorService.getAllProveedor()); // Listar Productos
	    return "admin/proveedor";
	}
	
	@PostMapping("/saveProveedor")
	public String saveProveedor(@ModelAttribute Proveedor proveedor, HttpSession session) throws IOException {
		
		Proveedor saveProveedor = proveedorService.saveProveedor(proveedor);
		
		if(ObjectUtils.isEmpty(saveProveedor)) {
			session.setAttribute("errorMsg", "No se pudo guardar. Error interno del servidor");
		}else {
			session.setAttribute("successMsg", "Registro Satisfactorio");
		}
		return "redirect:/admin/proveedor";
	}
	
	@GetMapping("/deleteProveedor/{id}")
	public String deleteProveedor(@PathVariable Integer id, HttpSession session) {
		Boolean deleteProveedor = proveedorService.deleteProveedor(id);
		
		if(deleteProveedor) {
			session.setAttribute("successMsg", "Producto eliminado exitosamente");
		}else {
			session.setAttribute("errorMsg", "Error en el servidor");
		}
		return "redirect:/admin/proveedor";
	}

    // PROVEEDORES SERVICES	

	@GetMapping("/cliente")	
	public String cliente(Model model) {
	    //model.addAttribute("proveedor", new Proveedor()); // Agregar un objeto Producto vacío al modelo
		model.addAttribute("cliente", clienteService.getAllCliente()); // Listar Productos
	    return "admin/cliente";
	}
	
	@GetMapping("/deleteCliente/{id}")
	public String deleteCliente(@PathVariable Long id, HttpSession session) {
		Boolean deleteCliente = clienteService.deleteCliente(id);
		
		if(deleteCliente) {
			session.setAttribute("successMsg", "Cliente eliminado exitosamente");
		}else {
			session.setAttribute("errorMsg", "Error en el servidor");
		}
		return "redirect:/admin/cliente";
	}

    // PROVEEDORES SERVICES	

	@GetMapping("/ventas")	
	public String ventas(Model model) {
	    //model.addAttribute("proveedor", new Proveedor()); // Agregar un objeto Producto vacío al modelo
        model.addAttribute("ventas", detalleService.getAllDetalle());
	    return "admin/ventas";
	}
}