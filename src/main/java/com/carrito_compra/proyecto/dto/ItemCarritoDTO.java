package com.carrito_compra.proyecto.dto;

import com.carrito_compra.proyecto.entity.Producto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ItemCarritoDTO {
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public ItemCarritoDTO(Producto producto, int cantidad) {
        setNombreProducto(producto.getNombre());
        setPrecioUnitario(producto.getPrecio());
        setCantidad(cantidad);
        setSubtotal(cantidad * producto.getPrecio());
    }

    public ItemCarritoDTO() {
    }
}
