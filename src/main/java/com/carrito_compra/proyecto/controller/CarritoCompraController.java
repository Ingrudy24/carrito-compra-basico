package com.carrito_compra.proyecto.controller;

import com.carrito_compra.proyecto.dto.ItemCarritoDTO;
import com.carrito_compra.proyecto.entity.ItemCarrito;
import com.carrito_compra.proyecto.service.CarritoCompraService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://carritocompraproyecto.local:5500")
@RestController
@RequestMapping("/carrito")
public class CarritoCompraController {

    @Autowired
    private CarritoCompraService carritoCompraService;


    @GetMapping
    public List<ItemCarrito> verCarrito(
            @RequestParam String codUsuario) {

        return carritoCompraService.getAll(codUsuario);
    }


    @PostMapping
    public void agregarProducto(
            @RequestParam String codUsuario,
            @RequestParam String codigo,
            @RequestParam int cantidad) {

        carritoCompraService.agregar(
                codUsuario,
                codigo,
                cantidad
        );
    }


    @DeleteMapping("/{id}")
    public void eliminarProducto(
            @RequestParam String codUsuario,
            @PathVariable Long id) {

        carritoCompraService.eliminar(codUsuario, id);
    }


    @DeleteMapping("/vaciar")
    public void vaciarCarrito(
            @RequestParam String codUsuario) {

        carritoCompraService.vaciar(codUsuario);
    }


    @GetMapping("/resumen")
    public List<ItemCarritoDTO> obtenerResumen(
            @RequestParam String codUsuario) {

        return carritoCompraService.getResumenCarrito(codUsuario);
    }
}
