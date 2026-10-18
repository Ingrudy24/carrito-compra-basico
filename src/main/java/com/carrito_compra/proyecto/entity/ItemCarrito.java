package com.carrito_compra.proyecto.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "shopping_cart")
@Data
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idShoppingCart")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @Column(name = "cantidad")
    private int cantidad;

    public ItemCarrito(Long id, Producto producto, int cantidad) {
        setId(id);
        setProducto(producto);
        setCantidad(cantidad);

    }

    public ItemCarrito() {
    }
}
