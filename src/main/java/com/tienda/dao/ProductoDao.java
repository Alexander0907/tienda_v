package com.tienda.dao;

import com.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoDao extends JpaRepository<Producto, Long> {

    //Ejemplo de método utilizando Métodos de Query
    public List<Producto> findByPrecioBetweenOrderByDescripcion(double precioInf, double precioSup);

    //Ejemplo de método utilizando Consultas con JPQL
    @Query(value = "SELECT a FROM Producto a where a.precio BETWEEN :precioInf AND :precioSup ORDER BY a.descripcion ASC")
    public List<Producto> metodoJPQL(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);

    //Ejemplo de método utilizando Consultas con SQL nativo
    @Query(nativeQuery = true,
            value = "SELECT * FROM producto where producto.precio BETWEEN :precioInf AND :precioSup ORDER BY producto.descripcion ASC")
    public List<Producto> metodoNativo(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);

    // Consulta para encontrar productos por rango exacto de existencias
    @Query("SELECT p FROM Producto p WHERE p.existencias BETWEEN :min AND :max")
    List<Producto> findProductosByExistenciasRange(@Param("min") int min, @Param("max") int max);

    @Query("SELECT p FROM Producto p WHERE p.existencias BETWEEN :min AND :max ORDER BY p.descripcion")
    List<Producto> findProductosByExistenciasJPQL(@Param("min") int min, @Param("max") int max);

    @Query(nativeQuery = true,
            value = "SELECT * FROM producto WHERE existencias BETWEEN :min AND :max ORDER BY descripcion")
    List<Producto> findProductosByExistenciasNativas(@Param("min") int min, @Param("max") int max);

}
