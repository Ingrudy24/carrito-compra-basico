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


    public List<ItemCarrito> getAll() {
        return itemCarritoRepository.findAll();
    }


    @Transactional
    public void agregar(String codigo, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        Producto producto = obtenerProductoPorCodigo(codigo);
        ItemCarrito itemExistente = obtenerItemExistente(codigo);

        if (itemExistente != null) {
            actualizarCantidad(itemExistente, cantidad);
        } else {
            crearNuevoItem(producto, cantidad);
        }
    }

    private Producto obtenerProductoPorCodigo(String codigo) {
        Producto producto = productoRepository.findByCodigo(codigo);
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado con código: " + codigo);
        }
        return producto;
    }

    private ItemCarrito obtenerItemExistente(String codigo) {
        return itemCarritoRepository.findByProductoCodigo(codigo);
    }

    private void actualizarCantidad(ItemCarrito item, int cantidad) {
        item.setCantidad(item.getCantidad() + cantidad);
        itemCarritoRepository.save(item);
    }

    private void crearNuevoItem(Producto producto, int cantidad) {
        ItemCarrito nuevoItem = new ItemCarrito(null, producto, cantidad);
        itemCarritoRepository.save(nuevoItem);
    }


    @Transactional
    public void eliminar(Long id) {
        itemCarritoRepository.deleteById(id);
    }


    @Transactional
    public void vaciar() {
        itemCarritoRepository.deleteAll();
    }


    public List<ItemCarritoDTO> getResumenCarrito() {
        return itemCarritoRepository.findAll().stream()
                .map(item -> new ItemCarritoDTO(item.getProducto(), item.getCantidad()))
                .toList();
    }
}

