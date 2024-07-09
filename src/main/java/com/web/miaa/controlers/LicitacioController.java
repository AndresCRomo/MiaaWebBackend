package com.web.miaa.controlers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.miaa.models.Licitacion;
import com.web.miaa.services.LicitacionIService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/licitacion")
@RequiredArgsConstructor

public class LicitacioController {
    private final LicitacionIService licitacionService;

    @GetMapping
    public ResponseEntity<List<Licitacion>> getLicitaciones(){
        return new ResponseEntity<>(licitacionService.getLicitaciones(), HttpStatus.FOUND);
    }
    @PostMapping(value="/add")
    public Licitacion addLicitacion( @RequestBody Licitacion licitacion){
        return licitacionService.addLicitacion(licitacion); 
    }
    @PutMapping(value="/update/{id}")
    public Licitacion updateLicitacion(@RequestBody Licitacion licitacion,@PathVariable Long id){
        return licitacionService.updateLicitacion(licitacion, id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteLicitacion(@PathVariable Long id){
        licitacionService.deleteLicitacion(id);
    }

    @GetMapping("/get/{id}")
    public Licitacion getLicitacionById(@PathVariable Long id){
        return licitacionService.getLicitacionById(id);
    }
}
