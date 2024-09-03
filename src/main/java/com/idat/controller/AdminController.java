package com.idat.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.idat.model.Categoria;
import com.idat.model.Producto;
import com.idat.model.Proveedor;
import com.idat.model.ResponseMessage;
import com.idat.model.Cliente;
import com.idat.model.ClienteUpdate;
import com.idat.model.Usuario;
import com.idat.model.UsuarioUpdate;
import com.idat.model.Venta;
import com.idat.model.Pedido;
import com.idat.model.ProductUpdate;
import com.idat.service.CategoriaServicio;
import com.idat.service.ClienteService;
import com.idat.service.DetalleVentaService;
import com.idat.service.ProductService;
import com.idat.service.ProveedorService;
import com.idat.service.UsuarioService;
import com.idat.service.VentaService;
import com.idat.service.impl.PedidoService;
import com.idat.service.impl.ProductServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


	@Autowired
	private CategoriaServicio categoriaServicio;

	@Autowired
    private ProductService productService;
	
	@Autowired
    private ProveedorService proveedorService;
	
	@Autowired
    private ClienteService clienteService;
	
	@Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private DetalleVentaService detalleService;
    
    @Autowired
    private PedidoService pedidoService;


	@GetMapping("/")
	public String index() {
		return "admin/index";
	}


	@GetMapping("/categoria")
	public String categoria(Model model) {
		model.addAttribute("categorias",categoriaServicio.getAllCategoria());
		
		return "admin/categoria";
	}

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
	
	
	@GetMapping("/addproducto")
	public String addProducto(@RequestParam(defaultValue = "0") int page, 
	                          @RequestParam(defaultValue = "15") int size, 
	                          Model model) {
	    Pageable pageable = PageRequest.of(page, size);
	    Page<Producto> paginaProductos = productService.getProductoPaginados(pageable);
	    model.addAttribute("producto", new Producto());
	    model.addAttribute("categorias", categoriaServicio.getAllCategoria());
	    model.addAttribute("paginaProductos", paginaProductos);
	    model.addAttribute("productos", paginaProductos.getContent());
	    logger.info("Página actual: " + paginaProductos.getNumber());
	    logger.info("Total de productos retornados: " + paginaProductos.getContent().size());
	    logger.info("Total de productos: " + paginaProductos.getTotalElements());
	    logger.info("Total de páginas: " + paginaProductos.getTotalPages());

	    return "admin/addproducto";
	}
	
	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute Producto product, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {
		
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

    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable("id") Integer id,@ModelAttribute ProductUpdate productUpdate, 
                                @RequestParam("file") MultipartFile image,
                                HttpSession session) throws IOException {
        // Obtener el nombre del archivo o usar un nombre predeterminado
        String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();

        // Actualizar el nombre del archivo en el objeto Producto
        productUpdate.setImage(imageName);

        Producto updatedProduct = productService.actualizarProducto(id, productUpdate, image);
	    if (updatedProduct != null) {
	        return "redirect:/admin/addproducto";
	    } else {
	        return "redirect:/admin/addproducto";
	    }
    }

    // PROVEEDORES SERVICES

	@GetMapping("/proveedor")	
	public String proveedor(Model model) {
	    model.addAttribute("proveedor", new Proveedor());
		model.addAttribute("proveedores",proveedorService.getAllProveedor());
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

    // CLIENTE SERVICES	

	@GetMapping("/cliente")	
	public String cliente(Model model) {
		model.addAttribute("cliente", clienteService.getAllCliente());
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

    // VENTAS SERVICES	

	@GetMapping("/ventas")	
	public String ventas(Model model) {
        model.addAttribute("ventas", detalleService.getAllDetalle());
	    return "admin/ventas";
	}


    @GetMapping("/venta")
    public String listarVentas(Model model) {
        List<Venta> ventas = ventaService.listar();
        model.addAttribute("ventas", ventas);
        return "admin/venta";
    }

    @GetMapping("/ventas/{id}")
    public String verDetalleVenta(@PathVariable("id") Long id, Model model) {
        List<Venta> ventas = ventaService.listar();
        model.addAttribute("ventas", ventas);
        Venta venta = ventaService.buscar(id);
        model.addAttribute("venta", venta);
        model.addAttribute("cliente", venta.getCliente());
        model.addAttribute("detalles", venta.getDetalleVenta());
        return "admin/venta";
    }
	//PEDIDOS SERVICES
    
    @GetMapping("/pedidos")
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
        model.addAttribute("pedidos", pedidos);
        return "admin/pedido";
    }

    @PostMapping("/pedidos/{idPedido}/estado")
    public String actualizarEstadoPedido(@PathVariable Long idPedido, @RequestParam("estadoPedido") String estadoPedido, Model model) {
        try {
            pedidoService.actualizarEstadoPedido(idPedido, estadoPedido);
            model.addAttribute("succMsg", "Estado del pedido actualizado con éxito.");
        } catch (Exception e) {
            model.addAttribute("errorMsg", "Error al actualizar el estado del pedido: " + e.getMessage());
        }
        return "redirect:/admin/pedidos";
    }
    
    //USUARIO SERVICES	

	@GetMapping("/usuario")	
	public String usuario(Model model) {
		model.addAttribute("usuarios", usuarioService.getAllUsuario()); // Listar Productos
	    return "admin/usuario";
	}
	
	@PostMapping("/saveUsuario")
	public String saveUsuario(@ModelAttribute Usuario usuario, HttpSession session) throws IOException {
		
		Usuario saveUsuario = usuarioService.saveUsuario(usuario);
		
		if(ObjectUtils.isEmpty(saveUsuario)) {
			session.setAttribute("errorMsg", "No se pudo guardar. Error interno del servidor");
		}else {
			session.setAttribute("successMsg", "Registro Satisfactorio");
		}
		return "redirect:/admin/usuario";
	}
	
	@GetMapping("/deleteUsuario/{id}")
	public String deleteUsuario(@PathVariable Long id, HttpSession session) {
		Boolean deleteUsuario = usuarioService.deleteUsuario(id);
		
		if(deleteUsuario) {
			session.setAttribute("successMsg", "Usuario eliminado exitosamente");
		}else {
			session.setAttribute("errorMsg", "Error en el servidor");
		}
		return "redirect:/admin/usuario";
	}
    
	@PostMapping("/updateUsuario/{id}")
	public String updateUsuario(@PathVariable("id") Long id, @ModelAttribute UsuarioUpdate usuarioUpdate) {
	    Usuario updatedUsuario = usuarioService.actualizarUsuario(id, usuarioUpdate);
	    if (updatedUsuario != null) {
	        return "redirect:/admin/usuario";
	    } else {
	        return "redirect:/admin/usuario";
	    }
	}
	
	@GetMapping("/loadEditUsuario/{id}")
	public String loadEditUsuario(@PathVariable Long id, Model model) {
		model.addAttribute("usuario",usuarioService.getUsuarioById(id));
		
		return "admin/edit_usuario";
	}
}