package org.mfigueroa.App.mappers;

import org.mfigueroa.App.dto.ProductoDTO;
import org.mfigueroa.App.model.Producto;

public class ProductoMapper {


    public static ProductoDTO toDto(Producto producto) {
        System.out.println("Producto mapper toDTO");
        System.out.println("ID PMDTO " + producto.getId());

        return
                new ProductoDTO(producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getStock());
    }

    public static Producto toEntity(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setId(productoDTO.getId());
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        System.out.println(producto.getNombre());
        return producto;
    }

}
