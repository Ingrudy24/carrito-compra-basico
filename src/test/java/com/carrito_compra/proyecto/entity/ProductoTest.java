package com.carrito_compra.proyecto.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {


    /**
     * Verifica que los métodos set y get funcionen correctamente
     * para todos los atributos de Producto con datos reales.
     */
    @Test
    void testSettersYGettersProducto() {
        Producto producto = new Producto();
        producto.setIdProducto(2L);
        producto.setNombre("EXP Portable Hard Drive");
        producto.setCodigo("USB02");
        producto.setPrecio(215.10);
        producto.setImagen("images/product/external-hard-drive.jpg");

        assertEquals(2L, producto.getIdProducto());
        assertEquals("EXP Portable Hard Drive", producto.getNombre());
        assertEquals("USB02", producto.getCodigo());
        assertEquals(215.10, producto.getPrecio(), 0.01);
        assertEquals("images/product/external-hard-drive.jpg", producto.getImagen());
    }

    /**
     * Verifica que el método toString incluya correctamente
     * los campos relevantes del producto con valores reales.
     */
    @Test
    void testToStringProducto() {
        Producto producto = new Producto(4L, "XP 1155 Intel Core Laptop", "LPN45", 10232.35, "images/product/laptop.jpg");
        String texto = producto.toString();

        assertTrue(texto.contains("XP 1155 Intel Core Laptop"));
        assertTrue(texto.contains("LPN45"));
        assertTrue(texto.contains("10232.35"));
    }
}
