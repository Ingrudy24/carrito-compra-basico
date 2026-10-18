package com.carrito_compra.proyecto.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="producto")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")
    private Long idProducto;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "precio")
    private double precio;

    @Column(name = "imagen")
    private String imagen;

    public Producto(Long idProducto, String nombre, String codigo, double precio, String imagen) {

        setIdProducto(idProducto);
        setNombre(nombre);
        setCodigo(codigo);
        setPrecio(precio);
        setImagen(imagen);

    }

    public Producto() {
    }

}
