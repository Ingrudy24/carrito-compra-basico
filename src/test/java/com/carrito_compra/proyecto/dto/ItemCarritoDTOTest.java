package com.carrito_compra.proyecto.dto;

import com.carrito_compra.proyecto.entity.Producto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemCarritoDTOTest {

    /**
     * Verifica que el constructor de ItemCarritoDTO asigne correctamente
     * el nombre del producto, precio unitario, cantidad y subtotal.
     */
    @Test
    void testConstructorDTO() {
        Producto producto = new Producto(1L, "FinePix Pro2 3D Camera", "CAM123", 671.21, "camara.jpg");
        int cantidad = 2;

        ItemCarritoDTO dto = new ItemCarritoDTO(producto, cantidad);

        assertEquals("FinePix Pro2 3D Camera", dto.getNombreProducto());
        assertEquals(671.21, dto.getPrecioUnitario(), 0.01);
        assertEquals(2, dto.getCantidad());
        assertEquals(1342.42, dto.getSubtotal(), 0.01);
    }

    /**
     * Verifica que el método toString incluya correctamente los campos relevantes
     * como nombre del producto, precio unitario y subtotal.
     */
    @Test
    void testToStringDTO() {
        Producto producto = new Producto(2L, "XP 1155 Intel Core Laptop", "LAP999", 10232.35, "laptop.jpg");
        ItemCarritoDTO dto = new ItemCarritoDTO(producto, 1);

        String texto = dto.toString();

        assertTrue(texto.contains("XP 1155 Intel Core Laptop"));
        assertTrue(texto.contains("10232.35"));
        assertTrue(texto.contains("subtotal=10232.35"));
    }
}
