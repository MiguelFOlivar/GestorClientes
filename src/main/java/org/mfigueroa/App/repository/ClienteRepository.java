package org.mfigueroa.App.repository;

import org.mfigueroa.App.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    // Obtener todos los clientes
    @Query("SELECT c FROM Cliente c")
    List<Cliente> findAllClientes();

    // Consulta JPQL para obtener clientes por correo
//    @Query("SELECT c FROM Cliente c WHERE c.email = :email")
//    Optional<Cliente> findByCorreo(@Param("email") String email);

    // Consulta JPQL para obtener clientes por coincidencia por el nombre
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Cliente> findByNombre(String nombre);

    // Actualizar el nombre del cliente por su ID
    @Modifying
    @Transactional
    @Query("UPDATE Cliente c SET c.nombre = :nombre WHERE c.id = :id")
    int updateClienteNombre(Long id, String nombre);

    // Eliminar cliente por ID
    @Modifying
    @Transactional
    @Query("DELETE FROM Cliente c WHERE c.id = :id")
    void deleteById(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cliente c WHERE c.id = :id")
    void eliminarClientePorId(@Param("id") Long id);

    // Consulta vulnerable para obtener cliente por correo
    // **Inseguro**: Se concatena directamente el parámetro "email" en la consulta
    @Query("SELECT c FROM Cliente c WHERE c.email = '" + ":email" + "'")
    Optional<Cliente> findByCorreo(String email);

    // Método vulnerable para eliminar un cliente por su ID
    // **Inseguro**: Se concatenan directamente el parámetro "id" en la consulta
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM Cliente c WHERE c.id = '" + ":id" + "'")
//    void deleteById(Long id);

    // Método para filtrar por nombre (como ejemplo)
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    // Método para filtrar por correo (como ejemplo)
    List<Cliente> findByEmailContainingIgnoreCase(String email);
}
