package com.carrito_compra.proyecto.controller;

import com.carrito_compra.proyecto.dto.ItemCarritoDTO;
import com.carrito_compra.proyecto.entity.ItemCarrito;
import com.carrito_compra.proyecto.service.CarritoCompraService;
import jakarta.annotation.PostConstruct;
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
    public List<ItemCarrito> verCarrito(){
        return carritoCompraService.getAll();
    }

    @PostMapping
    public void agregarProducto(@RequestParam String codigo, @RequestParam int cantidad){
        carritoCompraService.agregar(codigo, cantidad);
    }

    @DeleteMapping("/{id}")
    public void eleminarProducto(@PathVariable Long id) {
        carritoCompraService.eliminar(id);
    }

    @DeleteMapping("/vaciar")
    public void vaciarCarrito(){
        carritoCompraService.vaciar();
    }

    @GetMapping("/resumen")
    public List<ItemCarritoDTO> obtenerResumen() {
        return carritoCompraService.getResumenCarrito();
    }

}
