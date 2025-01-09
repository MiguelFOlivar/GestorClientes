package org.mfigueroa.App.repository;

import org.mfigueroa.App.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("SELECT v FROM Venta v WHERE v.cliente.id = :clienteId")
    List<Venta> findByClienteId(Long clienteId);

    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.cliente.id = :clienteId")
    double findTotalVentasByClienteId(Long clienteId);

    @Query("SELECT v FROM Venta v JOIN v.productos p WHERE p.id = :productoId")
    List<Venta> findVentasPorProducto(Long productoId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Venta v WHERE v.id = :id")
    int deleteVentaById(Long id);

    // Consulta UPDATE para actualizar el cliente de una venta
    @Modifying
    @Transactional
    @Query("UPDATE Venta v SET v.cliente.id = :clienteId WHERE v.id = :ventaId")
    void actualizarClienteDeVenta(@Param("clienteId") Long clienteId, @Param("ventaId") Long ventaId);

}
