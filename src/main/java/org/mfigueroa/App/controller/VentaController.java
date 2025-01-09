package org.mfigueroa.App.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.mfigueroa.App.dto.VentaDTO;
import org.mfigueroa.App.model.Venta;
import org.mfigueroa.App.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Hidden
@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping("/create")
    public ResponseEntity<VentaDTO> crearVenta(@RequestBody VentaDTO ventaDTO) {
        return new ResponseEntity<>(ventaService.crearVenta(ventaDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VentaDTO>> getAll() {
        return new ResponseEntity<>(ventaService.getAllVentas(), HttpStatus.FOUND);
    }

    @GetMapping("/findByCliente")
    public ResponseEntity<List<VentaDTO>> findByClienteId(@RequestParam Long id){
        List<VentaDTO> ventaDTOList = ventaService.findBiIdCliente(id);
        return new ResponseEntity<>(ventaDTOList, HttpStatus.FOUND);
    }

    @DeleteMapping("/deleteById")
    public boolean deleteVentaById(@RequestParam Long id){
        return ventaService.deleteVentaById(id);
    }

    @PutMapping("/updateClienteVenta")
    public void updateClienteVenta(@RequestParam Long idCliente, @RequestParam Long idVenta) {
        ventaService.updateVentaClienteId(idCliente, idVenta);
    }

}
