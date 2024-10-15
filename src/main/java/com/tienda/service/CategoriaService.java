package com.tienda.service;

import com.tienda.domain.Categoria;
import java.util.List;

public interface CategoriaService {
    List<Categoria> getCategorias(boolean activos);
}

