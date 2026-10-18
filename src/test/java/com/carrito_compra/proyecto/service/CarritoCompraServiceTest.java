package com.carrito_compra.proyecto.service;

import com.carrito_compra.proyecto.dto.ItemCarritoDTO;
import com.carrito_compra.proyecto.entity.ItemCarrito;
import com.carrito_compra.proyecto.entity.Producto;
import com.carrito_compra.proyecto.repository.ItemCarritoRepository;
import com.carrito_compra.proyecto.repository.ProductoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarritoCompraServiceTest {

    private static final String USUARIO = "usuario-prueba-123";

    @InjectMocks
    private CarritoCompraService carritoService;

    @Mock
    private ItemCarritoRepository itemCarritoRepository;

    @Mock
    private ProductoRepository productoRepository;


    @Test
    void testAgregarNuevoProducto() {

        Producto producto = new Producto(
                1L,
                "FinePix Pro2 3D Camera",
                "3DCAM01",
                671.21,
                "images/product/camera.jpg"
        );

        when(productoRepository.findByCodigo("3DCAM01"))
                .thenReturn(producto);

        when(itemCarritoRepository
                .findByCodUsuarioAndProducto_Codigo(
                        USUARIO, "3DCAM01"))
                .thenReturn(Optional.empty());

        carritoService.agregar(USUARIO, "3DCAM01", 2);

        ArgumentCaptor<ItemCarrito> captor =
                ArgumentCaptor.forClass(ItemCarrito.class);

        verify(itemCarritoRepository).save(captor.capture());

        ItemCarrito guardado = captor.getValue();

        assertEquals(producto, guardado.getProducto());
        assertEquals(USUARIO, guardado.getCodUsuario());
        assertEquals(2, guardado.getCantidad());
    }


    @Test
    void testActualizarCantidadProductoExistente() {

        Producto producto = new Producto(
                2L,
                "EXP Portable Hard Drive",
                "USB02",
                215.10,
                "images/product/external-hard-drive.jpg"
        );

        ItemCarrito existente = new ItemCarrito(
                10L,
                producto,
                USUARIO,
                1
        );

        when(productoRepository.findByCodigo("USB02"))
                .thenReturn(producto);

        when(itemCarritoRepository
                .findByCodUsuarioAndProducto_Codigo(
                        USUARIO, "USB02"))
                .thenReturn(Optional.of(existente));

        carritoService.agregar(USUARIO, "USB02", 3);

        assertEquals(4, existente.getCantidad());

        verify(itemCarritoRepository).save(existente);
    }


    @Test
    void testAgregarCantidadInvalida() {

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> carritoService.agregar(
                        USUARIO, "USB02", 0)
        );

        assertEquals(
                "La cantidad debe ser mayor que cero.",
                ex.getMessage()
        );

        verifyNoInteractions(
                productoRepository,
                itemCarritoRepository
        );
    }


    @Test
    void testEliminarProducto() {

        Producto producto = new Producto(
                1L,
                "FinePix Pro2 3D Camera",
                "3DCAM01",
                671.21,
                "images/product/camera.jpg"
        );

        ItemCarrito item = new ItemCarrito(
                5L,
                producto,
                USUARIO,
                1
        );

        when(itemCarritoRepository.findById(5L))
                .thenReturn(Optional.of(item));

        carritoService.eliminar(USUARIO, 5L);

        verify(itemCarritoRepository).delete(item);
    }


    @Test
    void testNoEliminarProductoDeOtroUsuario() {

        Producto producto = new Producto(
                1L,
                "FinePix Pro2 3D Camera",
                "3DCAM01",
                671.21,
                "images/product/camera.jpg"
        );

        ItemCarrito item = new ItemCarrito(
                5L,
                producto,
                "otro-usuario",
                1
        );

        when(itemCarritoRepository.findById(5L))
                .thenReturn(Optional.of(item));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> carritoService.eliminar(USUARIO, 5L)
        );

        assertEquals(
                "El producto no pertenece a este usuario.",
                ex.getMessage()
        );

        verify(itemCarritoRepository, never())
                .delete(any(ItemCarrito.class));
    }


    @Test
    void testVaciarCarrito() {

        carritoService.vaciar(USUARIO);

        verify(itemCarritoRepository)
                .deleteByCodUsuario(USUARIO);

        verify(itemCarritoRepository, never())
                .deleteAll();
    }


    @Test
    void testResumenCarrito() {

        Producto producto = new Producto(
                4L,
                "XP 1155 Intel Core Laptop",
                "LPN45",
                10232.35,
                "images/product/laptop.jpg"
        );

        ItemCarrito item = new ItemCarrito(
                20L,
                producto,
                USUARIO,
                1
        );

        when(itemCarritoRepository.findByCodUsuario(USUARIO))
                .thenReturn(List.of(item));

        List<ItemCarritoDTO> resumen =
                carritoService.getResumenCarrito(USUARIO);

        assertEquals(1, resumen.size());

        assertEquals(
                "XP 1155 Intel Core Laptop",
                resumen.get(0).getNombreProducto()
        );

        assertEquals(
                10232.35,
                resumen.get(0).getPrecioUnitario(),
                0.01
        );

        assertEquals(1, resumen.get(0).getCantidad());

        assertEquals(
                10232.35,
                resumen.get(0).getSubtotal(),
                0.01
        );
    }


    @Test
    void testConsultarCarritoPorUsuario() {

        Producto producto = new Producto(
                1L,
                "FinePix Pro2 3D Camera",
                "3DCAM01",
                671.21,
                "images/product/camera.jpg"
        );

        ItemCarrito item = new ItemCarrito(
                5L,
                producto,
                USUARIO,
                2
        );

        when(itemCarritoRepository.findByCodUsuario(USUARIO))
                .thenReturn(List.of(item));

        List<ItemCarrito> resultado =
                carritoService.getAll(USUARIO);

        assertEquals(1, resultado.size());
        assertEquals(USUARIO, resultado.get(0).getCodUsuario());

        verify(itemCarritoRepository)
                .findByCodUsuario(USUARIO);
    }
}
