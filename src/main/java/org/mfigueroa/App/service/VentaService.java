package org.mfigueroa.App.service;

import org.mfigueroa.App.dto.VentaDTO;
import org.mfigueroa.App.mappers.VentaMapper;
import org.mfigueroa.App.model.Venta;
import org.mfigueroa.App.repository.ClienteRepository;
import org.mfigueroa.App.repository.ProductoRepository;
import org.mfigueroa.App.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentaMapper ventaMapper;

    public VentaDTO crearVenta(VentaDTO ventaDTO) {
        Venta venta = ventaRepository.save(ventaMapper.toEntity(ventaDTO));
        return ventaMapper.toDto(venta);
    }

    public List<VentaDTO> getAllVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        List<VentaDTO> ventaDTOList = ventas.stream()
                .map(v -> ventaMapper.toDto(v)).toList();
        return ventaDTOList;
    }

    public List<VentaDTO> findBiIdCliente(Long id) {
        List<Venta> ventas = ventaRepository.findByClienteId(id);
        return ventas.stream().map(v -> ventaMapper.toDto(v)).toList();
    }

    public boolean deleteVentaById(Long id){
        int eliminado = ventaRepository.deleteVentaById(id);
        return eliminado > 0;
    }

    public void updateVentaClienteId(Long idCliente, Long idVenta){
        ventaRepository.actualizarClienteDeVenta(idCliente, idVenta);
    }

}
