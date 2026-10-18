package com.carrito_compra.proyecto.repository;

import com.carrito_compra.proyecto.entity.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemCarritoRepository
        extends JpaRepository<ItemCarrito, Long> {


    List<ItemCarrito> findByCodUsuario(String codUsuario);


    Optional<ItemCarrito> findByCodUsuarioAndProducto_Codigo(
            String codUsuario,
            String codigo
    );


    void deleteByCodUsuario(String codUsuario);
}
