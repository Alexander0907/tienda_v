package com.tienda.controller;

import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tienda.service.ProductoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pruebas")
public class PruebasController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos(false);
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }

    @GetMapping("/listado/{idCategoria}")
    public String listado(Model model, Categoria categoria) {
        var productos = categoriaService.getCategoria(categoria).getProductos();
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }

    //Los métodos siguientes son para la prueba de consultas ampliadas
    @GetMapping("/consulta")
    public String listado2(Model model) {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        return "/pruebas/consulta";
    }

    @PostMapping("/query1")
    public String consultaQuery1(@RequestParam(value = "precioInf") double precioInf,
            @RequestParam(value = "precioSup") double precioSup, Model model) {
        var productos = productoService.findByPrecioBetweenOrderByDescripcion(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        model.addAttribute("totalProductos", productos.size());
        return "/pruebas/consulta";
    }

    @PostMapping("/query2")
    public String consultaQuery2(@RequestParam(value = "precioInf") double precioInf,
            @RequestParam(value = "precioSup") double precioSup, Model model) {
        var productos = productoService.metodoJPQL(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/consulta";
    }

    @PostMapping("/query3")
    public String consultaQuery3(@RequestParam(value = "precioInf") double precioInf,
            @RequestParam(value = "precioSup") double precioSup, Model model) {
        var productos = productoService.metodoNativo(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/consulta";
    }

    @PostMapping("/query4")
    public String consultaExistencias(@RequestParam(value = "min") int min,
            @RequestParam(value = "max") int max,
            Model model) {
        // Realiza la consulta a través del servicio
        var productos = productoService.findProductosByExistenciasRange(min, max);
        model.addAttribute("productos", productos);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("totalProductos", productos.size());
        return "/pruebas/consulta"; // Redirige a la vista con los resultados
    }

    @PostMapping("/query5")
    public String consultaExistenciasJPQL(@RequestParam(value = "min") int min,
            @RequestParam(value = "max") int max,
            Model model) {
        var productos = productoService.findProductosByExistenciasJPQL(min, max);
        model.addAttribute("productos", productos);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("totalProductos", productos.size());
        return "/pruebas/consulta"; // Redirige a la misma vista de resultados
    }

    @PostMapping("/query6")
    public String consultaExistenciasNativas(@RequestParam(value = "min") int min,
            @RequestParam(value = "max") int max,
            Model model) {
        var productos = productoService.findProductosByExistenciasNativas(min, max);
        model.addAttribute("productos", productos);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("totalProductos", productos.size());
        return "/pruebas/consulta"; // Redirige a la misma vista de resultados
    }

}
