package com.carrito_compra.proyecto.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemCarritoTest {

    /**
     * Verifica que el constructor con parámetros asigne correctamente
     * los valores de id, producto y cantidad.
     */
    @Test
    void testConstructorItemCarrito() {
        Producto producto = new Producto(1L, "FinePix Pro2 3D Camera", "3DCAM01", 671.21, "images/product/camera.jpg");
        ItemCarrito item = new ItemCarrito(10L, producto, 2);

        assertEquals(10L, item.getId());
        assertEquals(producto, item.getProducto());
        assertEquals(2, item.getCantidad());
    }

    /**
     * Verifica que los métodos set y get funcionen correctamente
     * para todos los atributos de ItemCarrito.
     */
    @Test
    void testSettersYGettersItemCarrito() {
        Producto producto = new Producto(2L, "EXP Portable Hard Drive", "USB02", 215.10, "images/product/external-hard-drive.jpg");
        ItemCarrito item = new ItemCarrito();
        item.setId(20L);
        item.setProducto(producto);
        item.setCantidad(3);

        assertEquals(20L, item.getId());
        assertEquals(producto, item.getProducto());
        assertEquals(3, item.getCantidad());
    }

    /**
     * Verifica que el método toString incluya correctamente
     * los campos relevantes del item y del producto asociado.
     */
    @Test
    void testToStringItemCarrito() {
        Producto producto = new Producto(4L, "XP 1155 Intel Core Laptop", "LPN45", 10232.35, "images/product/laptop.jpg");
        ItemCarrito item = new ItemCarrito(30L, producto, 1);

        String texto = item.toString();

        assertTrue(texto.contains("XP 1155 Intel Core Laptop"));
        assertTrue(texto.contains("10232.35"));
        assertTrue(texto.contains("cantidad=1"));
    }
}
