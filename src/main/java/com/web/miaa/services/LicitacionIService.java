package com.web.miaa.services;

import java.util.List; // Import the correct List class
import com.web.miaa.models.Licitacion;

public interface LicitacionIService {
    
    Licitacion addLicitacion(Licitacion licitacion);
    List<Licitacion> getLicitaciones(); // Fix the List class
    Licitacion updateLicitacion(Licitacion licitacion,Long id);
    Licitacion getLicitacionById(Long id);
    void deleteLicitacion(Long id); 
} 
