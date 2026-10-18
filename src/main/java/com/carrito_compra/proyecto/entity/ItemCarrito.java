package com.carrito_compra.proyecto.entity;

import jakarta.persistence.*;
import lombok.Data;

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

    @Column(name = "codUsuario", length = 128)
    private String codUsuario;

    @Column(name = "cantidad")
    private int cantidad;

    public ItemCarrito() {
    }

    public ItemCarrito(Long id, Producto producto, int cantidad) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public ItemCarrito(Long id, Producto producto,
                       String codUsuario, int cantidad) {
        this.id = id;
        this.producto = producto;
        this.codUsuario = codUsuario;
        this.cantidad = cantidad;
    }
}
