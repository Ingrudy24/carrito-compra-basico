package com.carrito_compra.proyecto.service;

import com.carrito_compra.proyecto.dto.ItemCarritoDTO;
import com.carrito_compra.proyecto.entity.ItemCarrito;
import com.carrito_compra.proyecto.entity.Producto;
import com.carrito_compra.proyecto.repository.ItemCarritoRepository;
import com.carrito_compra.proyecto.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CarritoCompraServiceTest {

    @InjectMocks
    private CarritoCompraService carritoService;

    @Mock
    private ItemCarritoRepository itemCarritoRepository;

    @Mock
    private ProductoRepository productoRepository;

    /**
     * Verifica que se agregue un nuevo producto al carrito cuando no existe previamente.
     */
    @Test
    void testAgregarNuevoProducto() {
        Producto producto = new Producto(1L, "FinePix Pro2 3D Camera", "3DCAM01", 671.21, "images/product/camera.jpg");

        when(productoRepository.findByCodigo("3DCAM01")).thenReturn(producto);
        when(itemCarritoRepository.findByProductoCodigo("3DCAM01")).thenReturn(null);

        carritoService.agregar("3DCAM01", 2);

        ArgumentCaptor<ItemCarrito> captor = ArgumentCaptor.forClass(ItemCarrito.class);
        verify(itemCarritoRepository).save(captor.capture());

        ItemCarrito guardado = captor.getValue();
        assertEquals(producto, guardado.getProducto());
        assertEquals(2, guardado.getCantidad());
    }

    /**
     * Verifica que se actualice la cantidad de un producto existente en el carrito.
     */
    @Test
    void testActualizarCantidadProductoExistente() {
        Producto producto = new Producto(2L, "EXP Portable Hard Drive", "USB02", 215.10, "images/product/external-hard-drive.jpg");
        ItemCarrito existente = new ItemCarrito(10L, producto, 1);

        when(productoRepository.findByCodigo("USB02")).thenReturn(producto);
        when(itemCarritoRepository.findByProductoCodigo("USB02")).thenReturn(existente);

        carritoService.agregar("USB02", 3);

        assertEquals(4, existente.getCantidad());
        verify(itemCarritoRepository).save(existente);
    }

    /**
     * Verifica que se lance una excepción si se intenta agregar una cantidad inválida.
     */
    @Test
    void testAgregarCantidadInvalida() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            carritoService.agregar("USB02", 0);
        });

        assertEquals("La cantidad debe ser mayor que cero.", ex.getMessage());
    }

    /**
     * Verifica que se elimine un producto del carrito por su ID.
     */
    @Test
    void testEliminarProducto() {
        carritoService.eliminar(5L);
        verify(itemCarritoRepository).deleteById(5L);
    }

    /**
     * Verifica que se vacíe completamente el carrito.
     */
    @Test
    void testVaciarCarrito() {
        carritoService.vaciar();
        verify(itemCarritoRepository).deleteAll();
    }

    /**
     * Verifica que se genere correctamente el resumen del carrito en formato DTO.
     */
    @Test
    void testResumenCarrito() {
        Producto producto = new Producto(4L, "XP 1155 Intel Core Laptop", "LPN45", 10232.35, "images/product/laptop.jpg");
        ItemCarrito item = new ItemCarrito(20L, producto, 1);

        when(itemCarritoRepository.findAll()).thenReturn(List.of(item));

        List<ItemCarritoDTO> resumen = carritoService.getResumenCarrito();

        assertEquals(1, resumen.size());
        assertEquals("XP 1155 Intel Core Laptop", resumen.get(0).getNombreProducto());
        assertEquals(10232.35, resumen.get(0).getPrecioUnitario(), 0.01);
        assertEquals(1, resumen.get(0).getCantidad());
        assertEquals(10232.35, resumen.get(0).getSubtotal(), 0.01);
    }
}


