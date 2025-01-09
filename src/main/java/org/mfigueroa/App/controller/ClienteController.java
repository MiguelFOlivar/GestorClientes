package org.mfigueroa.App.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mfigueroa.App.dto.ClienteDTO;
import org.mfigueroa.App.service.ClienteServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestion de los clientes
 * permite realizar operaciones CRUD sobre los clientes
 */
@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes Controller", description = "Operaciones CRUD para la gestión de Clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteServiceImpl clienteService;

    /**
     * crea un nuevo cliente
     *
     * @param clienteDTO Los datos del cliente a crear
     * @return El cliente creado
     */
    @PostMapping("/create")
    @Operation(
            summary = "Creación de cliente nuevo",
            description = "Permite la creación de un cliente nuevo",
            tags = {"Administración Clientes"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del cliente (nombre y correo)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClienteController.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Cliente de ejemplo",
                                            value = "{\"nombre\":\"Juan Pérez\",\"email\":\"juan@mail.com\"}",
                                            description = "Se deben ingresar el nombre y correo del cliente"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cliente creado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClienteDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Cliente de ejemplo",
                                                    value = "{\"nombre\":\"Juan Pérez\",\"email\":\"juan@mail.com\"}",
                                                    description = "Se devuelve la información del cliente creado"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno al crear el cliente",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<?> guardarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            logger.info("Iniciando creacion del cliente: {}", clienteDTO);
            ClienteDTO clienteGuardado = clienteService.guardarCliente(clienteDTO);
            logger.info("Cliente guardado exitosamente: {}", clienteGuardado);
            return new ResponseEntity<>(clienteGuardado, HttpStatus.CREATED);
        } catch (Exception ex) {
            logger.error("Error al guardar cliente: {}", ex.getMessage(), ex);
            // Respuesta de error con detalles
            String errorMessage = "Error al guardar el cliente: " + ex.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para crear múltiples clientes

    /**
     * Crea una lista de clientes
     *
     * @param clientesDTO Lista de clientes
     * @return status code 200 ok
     */
    @PostMapping("/batch")
    @Operation(
            summary = "Creación de Lista de clientes",
            description = "Permite la creación de una lista de cliente",
            tags = {"Administración Clientes"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lista con los datos de los clientes (nombre y correo)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClienteController.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Cliente de ejemplo",
                                            value = "[{\"nombre\":\"Juan Pérez\",\"email\":\"juan@mail.com\"}," +
                                                    "{\"nombre\":\"John Doe\",\"email\":\"jdoe@mail.com\"}]",
                                            description = "Devuelve la información del cliente creado"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Clientes creados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClienteDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno al crear lista de clientes",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<?> crearClientes(@RequestBody List<ClienteDTO> clientesDTO) {
        try {
            logger.info("Iniciando creacion de clientes en batch");
            clienteService.crearClientes(clientesDTO);
            logger.info("Clientes creados exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception ex) {
            logger.error("Error al crear clientes en batch: {}", ex.getMessage(), ex);
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseEntity.badRequest().body("Error creando usuario");
        }
    }

    /**
     * Consulta los clientes guardados en la base de datos
     *
     * @return status code 200, Lista de clientes
     */
    @GetMapping
    @Operation(summary = "Consulta clientes",
            description = "Permite consultar los clientes existentes en la BD",
            tags = {"Administración Clientes"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Clientes consultados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClienteController.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Cliente de ejemplo",
                                                    value = "[{\"nombre\":\"Juan Pérez\",\"email\":\"juan@mail.com\"}," +
                                                            "{\"nombre\":\"John Doe\",\"email\":\"jdoe@mail.com\"}]",
                                                    description = "Devuelve una lista con los clientes de la BD"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno obtener clientes",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<List<ClienteDTO>> findAll() {
        try {
            logger.info("Consultando todos los clientes");
            List<ClienteDTO> clientes = clienteService.getAllClientes();
            logger.info("Clientes encontrados: {}", clientes.size());
            return new ResponseEntity<>(clientes, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error al obtener todos los clientes: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * @return lista con los clientes existentes
     */
    @GetMapping("/findAll")
    @Operation(summary = "Consulta clientes",
            description = "Permite consultar los clientes existentes en la BD",
            tags = {"Administración Clientes"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Clientes consultados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClienteDTO.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Cliente de ejemplo",
                                                    value = "[{\"nombre\":\"Juan Pérez\",\"email\":\"juan@mail.com\"}," +
                                                            "{\"nombre\":\"John Doe\",\"email\":\"jdoe@mail.com\"}]",
                                                    description = "Devuelve una lista con los clientes de la BD"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno obtener clientes",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<List<ClienteDTO>> findAllClientes() {
        try {
            logger.info("Consultando clientes desde findAll.");
            List<ClienteDTO> clientes = clienteService.findAllClientes();
            logger.info("Clientes encontrados: {}", clientes.size());
            return new ResponseEntity<>(clientes, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al obtener clientes desde findAll: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Hidden
    @GetMapping("/getByEmail")
    public ResponseEntity<ClienteDTO> findByEmail(@RequestParam String email) {
        try {
            logger.info("Consultando cliente por email: {}", email);
            ClienteDTO cliente = clienteService.findByEmail(email);
            if (cliente == null) {
                logger.warn("No se encontró cliente con el email: {}", email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            logger.info("Cliente encontrado: {}", cliente);
            return new ResponseEntity<>(cliente, HttpStatus.FOUND);
        } catch (Exception e) {
            logger.error("Error al consultar cliente por email: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Hidden
    @GetMapping("/getByName")
    public ResponseEntity<List<ClienteDTO>> findByNombre(@RequestParam String nombre) {
        return new ResponseEntity<>(clienteService.findByNombre(nombre), HttpStatus.FOUND);
    }

    /**
     * Actualizar datos de un cliente existente
     *
     * @param id  id del cliente a actualizar
     * @param clienteDetails información nueva del cliente
     * @return cliente actualizado
     */
    @PutMapping("/update")
    @Operation(
            summary = "Actualizar Cliente",
            description = "Permite actualizar la información de un cliente existente",
            tags = {"Administración Clientes"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Información actualizada del cliente",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClienteController.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Cliente de ejemplo",
                                            value = "{\"nombre\":\"Juan Pérez\",\"email\":\"juan@mail.com\"}",
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
                                            value = "{\"nombre\":\"Nuevo Nombre\",\"email\":\"nuevoMail@mail.com\"}",
                                            description = "Se devuelve el cliente actualizado"
                                    )
                            }
                    )
            ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno al actualizar cliente",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<ClienteDTO> updateCliente(@RequestParam Long id, @RequestBody ClienteDTO clienteDetails) {
        try {
            logger.info("Actualizando cliente con ID: {}", id);
            ClienteDTO clienteActualizado = clienteService.updateCliente(id, clienteDetails);
            logger.info("Cliente actualizado exitosamente: {}", clienteActualizado);
            return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al actualizar cliente: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Hidden
    @PutMapping("/updateName")
    public boolean updateNombre(@RequestParam Long id, @RequestParam String nombre) {
        return clienteService.updateClienteNombre(id, nombre);
    }

    /**
     * Permite eliminar un cliente de la BD
     * @param id Id del cliente a eliminar
     * @return true al eliminar el cliente
     */
    @DeleteMapping("/deleteById")
    @Operation(
            summary = "Eliminar Cliente",
            description = "Permite eliminar un cliente específico de la BD",
            tags = {"Administración Clientes"},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Cliente eliminado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClienteDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno al eliminar cliente",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    public ResponseEntity<Void> deleteById(@RequestParam Long id) {
        try {
            logger.info("Eliminando cliente con ID: {}", id);
            clienteService.deleteById(id);
            logger.info("Cliente eliminado exitosamente.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            logger.error("Error al eliminar cliente: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Hidden
    @DeleteMapping("/delete")
    public ResponseEntity<ClienteDTO> deleteCliente(@RequestParam Long id) {
        try {
            logger.info("Eliminando cliente con ID: {}", id);
            ClienteDTO clienteEliminado = clienteService.deleteCliente(id);
            logger.info("Cliente eliminado exitosamente: {}", clienteEliminado);
            return new ResponseEntity<>(clienteEliminado, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al eliminar cliente: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
