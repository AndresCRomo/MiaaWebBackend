package com.web.miaa.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web.miaa.exceptions.LicitacionNotFoundException;
import com.web.miaa.exceptions.licitacionAlreadyExistsException;
import com.web.miaa.models.Licitacion;
import com.web.miaa.repository.LicitacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class LicitacionService implements LicitacionIService{
    private final LicitacionRepository licitacionRepository;
    @Override
    public List<Licitacion> getLicitaciones() {
        return licitacionRepository.findAll();
    }

    @Override
    public Licitacion addLicitacion(Licitacion licitacion) {
        if ( licitacionAlreadyExists(licitacion.getClave()) ) {
            throw new licitacionAlreadyExistsException(licitacion.getClave()+"La Licitacion ya existe ");
        }
        
        return licitacionRepository.save(licitacion);

    }


    
    @Override
    public Licitacion updateLicitacion(Licitacion licitacion, Long id) {
        return licitacionRepository.findById(id)
                .map(licitacionActual -> {
                    licitacionActual.setClave(licitacion.getClave());
                    licitacionActual.setTipo(licitacion.getTipo());
                    licitacionActual.setTitulo(licitacion.getTitulo());
                    licitacionActual.setConcepto(licitacion.getConcepto());
                    licitacionActual.setFechadisposicion(licitacion.getFechadisposicion());
                    licitacionActual.setFechajuntaaclaraciones(licitacion.getFechajuntaaclaraciones());
                    licitacionActual.setFechainscripcion(licitacion.getFechainscripcion());
                    licitacionActual.setFechalecturafallo(licitacion.getFechalecturafallo());
                    licitacionActual.setFechaventa(licitacion.getFechaventa());
                    licitacionActual.setFechaaperturatecnica(licitacion.getFechaaperturatecnica());
                    licitacionActual.setFechafallotecnico(licitacion.getFechafallotecnico());
                    licitacionActual.setFechafalloadjudicacion(licitacion.getFechafalloadjudicacion());
                    return licitacionRepository.save(licitacionActual);
                }).orElseThrow(() -> new LicitacionNotFoundException("La licitacion no pudo ser Encontrada")); 
    }
    
    @Override
    public Licitacion getLicitacionById(Long id) {
        return licitacionRepository.findById(id)
                .orElseThrow(() -> new LicitacionNotFoundException("La Licitacion con el id "+id+" no pudo ser Encontrada"));
    }
    
    @Override
    public void deleteLicitacion(Long id) {
        if ( !licitacionRepository.existsById(id) ) {
            throw new LicitacionNotFoundException("La Licitacion con el id "+id+" no pudo ser Encontrada");
        }
        licitacionRepository.deleteById(id);
    }
    
    private boolean licitacionAlreadyExists(String clave) {
        return licitacionRepository.findByClave(clave).isPresent();
    }
}
