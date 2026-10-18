package com.carrito_compra.proyecto.repository;

import com.carrito_compra.proyecto.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Producto findByCodigo(String codigo);

}
