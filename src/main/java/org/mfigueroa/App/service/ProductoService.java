package org.mfigueroa.App.service;

import org.mfigueroa.App.dto.ProductoDTO;
import org.mfigueroa.App.mappers.ProductoMapper;
import org.mfigueroa.App.model.Producto;
import org.mfigueroa.App.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoDTO> getAllProductos() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoDTO> productosDTO = new ArrayList<>();
        for (Producto p: productos){
            productosDTO.add(ProductoMapper.toDto(p));
        }

        return productosDTO;
    }

    public ProductoDTO saveProducto(ProductoDTO productoDTO) {
       Producto producto = ProductoMapper.toEntity(productoDTO);
       Producto productoGuardado = productoRepository.save(producto);
       return ProductoMapper.toDto(productoGuardado);
    }

    public Page<ProductoDTO> buscarProductoPorNombre(String nombre, Pageable pageable) {
        if (nombre  == null || nombre.isEmpty()) {
            return productoRepository.findAll(pageable).map(ProductoMapper::toDto);
        }
       return productoRepository.findByNombre(nombre, pageable).map(ProductoMapper::toDto);
    }


    // Buscar productos por precio
    public List<ProductoDTO> buscarProductoPorPrecio(double precio) {
        List<Producto> productos = productoRepository.findByPrecioMenorA(precio);
        return productos.stream().map(ProductoMapper::toDto).collect(Collectors.toList());
    }

    // Obtener productos en stock
    public List<ProductoDTO> obtenerProductosEnStock() {
        List<Producto> productos = productoRepository.findProductosEnStock();
        return productos.stream().map(ProductoMapper::toDto).collect(Collectors.toList());
    }


    public ProductoDTO eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        productoRepository.deleteById(id);
        return ProductoMapper.toDto(producto);
    }

    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDetails) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El producto no existe"));
        producto.setNombre(productoDetails.getNombre());
        producto.setPrecio(productoDetails.getPrecio());
        producto.setStock(productoDetails.getStock());
        productoRepository.save(producto);
        return ProductoMapper.toDto(producto);
    }

    public boolean actualizarPrecioProducto(Long id, double nuevoPrecio){
        int updated = productoRepository.updateProductoPrecio(id, nuevoPrecio);
        return  updated > 0; // Si se actualizó, devuelve true
    }

    // Método para crear múltiples productos
    public void crearProductos(List<ProductoDTO> productosDTO) {
        for (ProductoDTO productoDTO : productosDTO) {
            Producto producto = new Producto();
            producto.setNombre(productoDTO.getNombre());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setStock(productoDTO.getStock());
            productoRepository.save(producto); // Guardamos el producto en la base de datos
        }
    }


    public Page<ProductoDTO> obtenerProductosPorRangoDePrecio(String ordenarPor, Double minPrecio, Double maxPrecio, int pagina, int tamano){

        Pageable pageable = PageRequest.of(pagina, tamano, Sort.by(Sort.Order.by(ordenarPor)));

        Page<Producto> productos = productoRepository.findByPrecioBetween(minPrecio, maxPrecio, pageable);
        return productos.map(producto -> new ProductoDTO(producto.getId(), producto.getNombre(),
                producto.getPrecio(), producto.getStock()));
    }


    public Page<ProductoDTO> obtenerTodos(Pageable pageable) {
        return productoRepository.findAll(pageable).map(ProductoMapper::toDto);
    }


    public Pageable crearPageable(int pagina, int tamano, String ordenarPor, String orden) {
        Sort sort = Sort.by(Sort.Order.asc(ordenarPor));
        if ("desc".equalsIgnoreCase(orden)){
            sort = Sort.by(Sort.Order.desc(ordenarPor));
        }
        return PageRequest.of(pagina, tamano, sort);
    }

}
