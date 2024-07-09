package com.web.miaa.repository;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.data.jpa.repository.JpaRepository;


import com.web.miaa.models.Licitacion;

public interface LicitacionRepository extends JpaRepository<Licitacion, Long> {
    Optional<Licitacion> findByClave(String clave);
    Optional<Licitacion> findById(Long id);
}
