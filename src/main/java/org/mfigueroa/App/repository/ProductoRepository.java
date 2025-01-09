package org.mfigueroa.App.repository;
import org.mfigueroa.App.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Consulta JPQL para obtener productos por precio
    @Query("SELECT p FROM Producto p WHERE p.precio <= :precio")
    List<Producto> findByPrecioMenorA(@Param("precio") double precio);

    // Consulta JPQL para obtener productos en stock
    @Query("SELECT p FROM Producto p WHERE p.stock > 0")
    List<Producto> findProductosEnStock();

    // Actualizar el precio de un producto por su ID
    @Modifying
    @Transactional
    @Query("UPDATE Producto p SET p.precio = :precio WHERE p.id = :id")
    int updateProductoPrecio(Long id, double precio);

    // Actualizar el precio de un producto por su id
    @Modifying
    @Transactional
    @Query("UPDATE Producto p SET p.precio = :precio WHERE p.id = :id")
    void actualizarPrecio(@Param("id") Long id, @Param("precio") double precio);


    // Consulta JPQL para obtener productos por nombre
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Producto> findByNombre(@Param("nombre") String nombre, Pageable pageable);

    //Obtener productos de rango de precio
    Page<Producto>findByPrecioBetween(Double minPrecio, Double maxPrecio, Pageable pageable);

    // Obtener todos los productos con paginación y ordenación
    @Override
    Page<Producto> findAll(Pageable pageable);
}

