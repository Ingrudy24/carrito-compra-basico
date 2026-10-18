package com.carrito_compra.proyecto.service;

import com.carrito_compra.proyecto.dto.ItemCarritoDTO;
import com.carrito_compra.proyecto.entity.ItemCarrito;
import com.carrito_compra.proyecto.entity.Producto;
import com.carrito_compra.proyecto.repository.ItemCarritoRepository;
import com.carrito_compra.proyecto.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarritoCompraService {

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Consultar el carrito de un usuario
    public List<ItemCarrito> getAll(String codUsuario) {
        validarUsuario(codUsuario);
        return itemCarritoRepository.findByCodUsuario(codUsuario);
    }

    // Agregar productos al carrito
    @Transactional
    public void agregar(String codUsuario, String codigo, int cantidad) {

        validarUsuario(codUsuario);

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero."
            );
        }

        Producto producto = productoRepository.findByCodigo(codigo);

        if (producto == null) {
            throw new IllegalArgumentException(
                    "Producto no encontrado con código: " + codigo
            );
        }

        ItemCarrito itemExistente = itemCarritoRepository
                .findByCodUsuarioAndProducto_Codigo(codUsuario, codigo)
                .orElse(null);

        if (itemExistente != null) {

            itemExistente.setCantidad(
                    itemExistente.getCantidad() + cantidad
            );

            itemCarritoRepository.save(itemExistente);

        } else {

            ItemCarrito nuevoItem = new ItemCarrito(
                    null,
                    producto,
                    codUsuario,
                    cantidad
            );

            itemCarritoRepository.save(nuevoItem);
        }
    }

    // Eliminar un producto del carrito
    @Transactional
    public void eliminar(String codUsuario, Long id) {

        validarUsuario(codUsuario);

        ItemCarrito item = itemCarritoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Producto del carrito no encontrado."
                ));

        if (!codUsuario.equals(item.getCodUsuario())) {
            throw new IllegalArgumentException(
                    "El producto no pertenece a este usuario."
            );
        }

        itemCarritoRepository.delete(item);
    }

    // Vaciar únicamente el carrito del usuario
    @Transactional
    public void vaciar(String codUsuario) {

        validarUsuario(codUsuario);

        itemCarritoRepository.deleteByCodUsuario(codUsuario);
    }

    // Obtener el resumen del carrito
    public List<ItemCarritoDTO> getResumenCarrito(String codUsuario) {

        validarUsuario(codUsuario);

        return itemCarritoRepository.findByCodUsuario(codUsuario)
                .stream()
                .map(item -> new ItemCarritoDTO(
                        item.getProducto(),
                        item.getCantidad()
                ))
                .toList();
    }

    // Validar el código del usuario
    private void validarUsuario(String codUsuario) {

        if (codUsuario == null || codUsuario.isBlank()) {
            throw new IllegalArgumentException(
                    "Debe indicar el código del usuario."
            );
        }
    }
}
