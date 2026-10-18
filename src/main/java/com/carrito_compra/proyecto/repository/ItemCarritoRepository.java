package com.carrito_compra.proyecto.repository;

import com.carrito_compra.proyecto.entity.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
    ItemCarrito findByProductoCodigo(String codigo);
}
