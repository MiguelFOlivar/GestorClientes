package org.mfigueroa.App.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mfigueroa.App.dto.ClienteDTO;
import org.mfigueroa.App.dto.ProductoDTO;
import org.mfigueroa.App.repository.ProductoRepository;
import org.mfigueroa.App.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Productos Controller", description = "Operaciones CRUD para la gestión de productos")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    /**
     * Crea un nuevo producto en el sistema
     * @param productoDTO los datos del producto a crear(nombre, precio y stock)
     * @return el producto creado con un código de estado HTTP 201
     */
    @PostMapping("/create")
    @Operation(
            summary = "Crear nuevo producto",
            description = "Este endPoint Permite la creación de un producto nuevo en el sistema",
            tags = {"Productos, Creación"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos necesarios para crear un nuevo producto",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Producto de ejemplo",
                                            value = "{\"nombre\":\"Monitor\",\n\"precio\":100.0,\n" +
                                                    "\"stock\":3}",
                                            description = "Se deben ingresar los datos del producto (nombre, precio y stock )"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Producto creado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Producto creado",
                                                    value = "{\"id\":1,\n\"nombre\":\"Mouse Razer\",\n\"stock\":5}",
                                                    description = "Respuesta cuando el producto es creado"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno al crear el producto",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            }

    )
    public ResponseEntity<ProductoDTO> saveProducto(@RequestBody ProductoDTO productoDTO) {
        try {
            logger.info("Iniciando la creación del producto: {}", productoDTO);
            ProductoDTO productoGuardado = productoService.saveProducto(productoDTO);
            logger.info("Producto guardado exitosamente: {}", productoGuardado);
            return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error al guardar producto: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Permite la consulta de los productos existentes en el sistema
     * @return lista de productos existentes
     */
    @GetMapping()
    @Operation(summary = "Consulta Productos en el sistema",
            description = "Permite consultar los productos que se encuentran almacenados en la BD",
            tags = {"Administración Productos"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Productos existentes",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Producto de ejemplo",
                                                    value = "[{\"nombre\":\"Monitor Asus\",\"precio\":3500, \"stock\":10}," +
                                                            "{\"nombre\":\"Unidad SSD\",\"precio\":1300, \"stock\":7}]",
                                                    description = "Devuelve una lista con los productos de la BD"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno obtener productos",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<List<ProductoDTO>>getAll() {
        try {
            logger.info("Consultando todos los productos.");
            List<ProductoDTO> productos = productoService.getAllProductos();
            logger.info("Productos encontrados: {}", productos.size());
            return new ResponseEntity<>(productos, HttpStatus.FOUND);
        } catch (Exception e) {
            logger.error("Error al obtener productos: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Hidden
    @GetMapping("/findByPrice")
    public ResponseEntity<List<ProductoDTO>> findByPrecio(@RequestParam double precio) {
        try {
            logger.info("Consultando productos por precio: {}", precio);
            List<ProductoDTO> productos = productoService.buscarProductoPorPrecio(precio);
            logger.info("Productos encontrados por precio: {}", productos.size());
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            logger.error("Error al consultar productos por precio: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Hidden
    @GetMapping("/findByStock")
    public ResponseEntity<List<ProductoDTO>> findByStock() {
        return ResponseEntity.ok(productoService.obtenerProductosEnStock());
    }

    /**
     *
     * @param id id del producto a actualizar
     * @param productoDetails información nueva del producto
     * @return producto con la nueva información
     */
    @PutMapping("/update")
    @Operation(
            summary = "Actualizar Producto",
            description = "Permite actualizar la información de un producto en el sistema",
            tags = {"Administración Productos"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Información actualizada del producto",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Producto de ejemplo",
                                            value = "{\"nombre\":\"Monitor Asus\",\"precio\":3500, \"stock\":10}",
                                            description = "Se deben ingresar el nombre y correo del cliente"
                                    )
                            }
                    )
            ),
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Cliente actualizado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClienteDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Información a actualizar",
                                            value = "{\"nombre\":\"Nuevo nombre\",\"precio Nuevo\":3500, \"stock actualizado\":10}",
                                            description = "Se devuelve el producto actualizado"
                                    )
                            }
                    )
            ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno al actualizar producto",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<ProductoDTO> actualizarProducto(@RequestParam Long id, @RequestBody ProductoDTO productoDetails) {
        try {
            logger.info("Actualizando producto con ID: {}", id);
            ProductoDTO productoActualizado = productoService.actualizarProducto(id, productoDetails);
            logger.info("Producto actualizado exitosamente: {}", productoActualizado);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            logger.error("Error al actualizar producto: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Hidden
    @PutMapping("/updatePrecio")
    public boolean updatePrecioProducto(@RequestParam Long id, @RequestParam double precioNuevo) {
        return productoService.actualizarPrecioProducto(id, precioNuevo);
    }

    @DeleteMapping("/delete")
    @Operation(
            summary = "Eliminar Producto",
            description = "Permite eliminar un producto específico de la BD",
            tags = {"Administración Productos"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto eliminado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Producto eliminado",
                                                    value = "{\"id\":1,\n\"nombre\":\"Mouse Razer\",\n\"stock\":5}",
                                                    description = "Respuesta cuando el producto es eliminado"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno al eliminar producto",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    public ResponseEntity<ProductoDTO> eliminarProducto(@RequestParam Long id) {
        try {
            logger.info("Eliminando producto con ID: {}", id);
            ProductoDTO productoEliminado = productoService.eliminarProducto(id);
            logger.info("Producto eliminado exitosamente: {}", productoEliminado);
            return ResponseEntity.ok(productoEliminado);
        } catch (Exception e) {
            logger.error("Error al eliminar producto: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint para crear múltiples productos
    @PostMapping("/batch")
    @Operation(
            summary = "Creación de Lista de Productos",
            description = "Permite la creación de una lista de productos en el sistema",
            tags = {"Administración Productos"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lista con los datos de los productos en el sistema (nombre, precio y stock)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClienteController.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Producto de ejemplo",
                                            value = "[{\"nombre\":\"Unidad SSD\",\"precio\":980, \"stock\":8},\n" +
                                                    "{\"nombre\":\"Mouse Razr\",\"precio\":540, \"stock\":13}]",
                                            description = "Se debe colocar la informacion completa de los productos a crear"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Productos creados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductoDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno al crear lista de productos",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<Void> crearProductos(@RequestBody List<ProductoDTO> productosDTO) {
        try {
            logger.info("Iniciando la creación de productos en batch.");
            productoService.crearProductos(productosDTO);
            logger.info("Productos creados exitosamente en batch.");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            logger.error("Error al crear productos en batch: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Obtener productos paginados y ordenados
    @GetMapping("/getPaged")
    public Page<ProductoDTO> findAllPaged(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamano,
            @RequestParam(defaultValue = "nombre") String ordenarPor,
            @RequestParam(defaultValue = "desc") String orden) {

        Pageable pageable = productoService.crearPageable(pagina, tamano, ordenarPor, orden);
        return productoService.obtenerTodos(pageable);
    }

    //Obtener productos dentro de un rango de precio
    @GetMapping("/precio")
    public Page<ProductoDTO>filtrarProductosPorPRecio(
            @RequestParam(defaultValue = "precio") String ordenarPor,
            @RequestParam(defaultValue = "0") Double minPrecio,
            @RequestParam(defaultValue = "1000000") Double maxPrecio,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamano){
        return productoService.obtenerProductosPorRangoDePrecio(ordenarPor, minPrecio, maxPrecio, pagina, tamano);
    }


    //Obtener productos por nombre
    @Hidden
    @GetMapping("/findByName")
    public ResponseEntity<Page<ProductoDTO>> findByNombre(
            @RequestParam String nombre,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamano,
            @RequestParam(defaultValue = "nombre") String ordenarPor,
            @RequestParam(defaultValue = "desc") String orden){

        try {
            Pageable pageable = productoService.crearPageable(pagina, tamano, ordenarPor, orden);
            logger.info("Consultando productos por nombre: {}", nombre);
            Page<ProductoDTO> productos = productoService.buscarProductoPorNombre(nombre, pageable);
            logger.info("Productos encontrados por nombre: {}", productos.stream().toList().size());
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            logger.error("Error al consultar productos por nombre: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

//    @GetMapping("/todos")
//    public Page<ProductoDTO> obtenerTodosLosProductos(
//            @RequestParam(defaultValue = "0") int pagina,
//            @RequestParam(defaultValue = "10") int tamano,
//            @RequestParam(defaultValue = "nombre") String ordenarPor,
//            @RequestParam(defaultValue = "asc") String orden) {
//
//    }


    }
