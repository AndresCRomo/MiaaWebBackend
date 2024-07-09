package com.web.miaa.models;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "licitacion")
public class Licitacion {
    @Id
    @GeneratedValue Long id;
    String clave;
    String tipo;
    String titulo;
    String concepto;
    String fechadisposicion;
    String fechajuntaaclaraciones;
    String fechainscripcion;
    String fechalecturafallo;    
    String fechaventa;
    String fechaaperturatecnica;
    String fechafallotecnico;
    String fechafalloadjudicacion;

    public Licitacion() {
    }
    

    @Override 
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Licitacion)){
            return false;
        }
        Licitacion licitacion = (Licitacion) o;
        return Objects.equals(this.id, licitacion.id) && Objects.equals(this.clave, licitacion.clave) && Objects.equals(this.tipo, licitacion.tipo) && Objects.equals(this.titulo, licitacion.titulo) && Objects.equals(this.concepto, licitacion.concepto) && Objects.equals(this.fechadisposicion, licitacion.fechadisposicion) && Objects.equals(this.fechajuntaaclaraciones, licitacion.fechajuntaaclaraciones) && Objects.equals(this.fechainscripcion, licitacion.fechainscripcion) && Objects.equals(this.fechalecturafallo, licitacion.fechalecturafallo) && Objects.equals(this.fechaventa, licitacion.fechaventa) && Objects.equals(this.fechaaperturatecnica, licitacion.fechaaperturatecnica) && Objects.equals(this.fechafallotecnico, licitacion.fechafallotecnico) && Objects.equals(this.fechafalloadjudicacion, licitacion.fechafalloadjudicacion);
    }

}
