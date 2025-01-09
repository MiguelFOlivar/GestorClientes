package org.mfigueroa.App.mappers;

import org.mfigueroa.App.dto.ClienteDTO;
import org.mfigueroa.App.dto.ProductoDTO;
import org.mfigueroa.App.dto.VentaDTO;
import org.mfigueroa.App.model.Cliente;
import org.mfigueroa.App.model.Producto;
import org.mfigueroa.App.model.Venta;
import org.mfigueroa.App.repository.ClienteRepository;
import org.mfigueroa.App.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaMapper {

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private  ProductoRepository productoRepository;

    public VentaDTO toDto(Venta venta) {
        List<ProductoDTO> productosDTO = new ArrayList<>();
        for(Producto producto: venta.getProductos()) {
            productosDTO.add(ProductoMapper.toDto(producto));
        }
        ClienteDTO clienteDTO = ClienteMapper.toDto(venta.getCliente());
        List<Long> productoIds = productosDTO.stream().mapToLong(p -> p.getId()).boxed().toList();
        return new VentaDTO(venta.getId(), clienteDTO.getId(), productoIds, venta.getTotal());
    }

    public  Venta toEntity(VentaDTO ventaDTO) {
        Venta venta = new Venta();
        venta.setId(ventaDTO.getId());
        Cliente cliente = clienteRepository.findById(ventaDTO.getClienteId())
                .orElseThrow( () -> new IllegalArgumentException("Cliente no encontrado"));
        venta.setCliente(cliente);

        List<Long> productosIDs = new ArrayList<>();
        List<Producto> productos = new ArrayList<>();

        for (Long productoId: ventaDTO.getProductosId()) {
            Producto producto = new Producto();
            productos.add(productoRepository.findById(productoId)
                    .orElseThrow( () -> new RuntimeException("Producto no encontrado")));
        }
        venta.setProductos(productos);
        venta.setTotal(productos.stream().mapToDouble(Producto::getPrecio).sum());
        return venta;
    }

}
