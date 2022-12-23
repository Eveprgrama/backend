
package com.eve.programacion.controller;

import com.eve.programacion.entity.Porcentaje;
import com.eve.programacion.service.SvPorcentaje;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("porcentaje")
@CrossOrigin(origins = "http://localhost:4200")
@Transactional
public class CPorcentaje {
    @Autowired SvPorcentaje servpor;
    
    @GetMapping("/lista")
    public List<Porcentaje> getPorcentaje() {
       return servpor.getPorcentaje();
    }
    
    @PostMapping ("/nueva")
    public String createPorcentaje(@RequestBody Porcentaje por) {
        servpor.savePorcentaje(por);
        //devuelve un string avisando si creó correctamente
        return "La habilidad fue creada correctamente";
    }
    
    @DeleteMapping ("/borrar/{id}")
    public String deletePorcentaje (@PathVariable int id){
        servpor.deletePorcentaje(id);
        //devuelve un string avisando que borró correctamente
        return "La Habilidad fue borrada correctamente";
    }
    
    @PutMapping ("/editar/{id}")
    public Porcentaje editPorcentaje (@PathVariable int id,
                                @RequestParam("titulo") String nuevoTitulo,
                                @RequestParam("progreso") int nuevoProgreso) {
        //busco la experiencia en cuestion
        Porcentaje porce = servpor.findPorcentaje(id);
        
        //esto tambien puede ir en un service
        //para desacoplar aún mejor el código en un nuevo método
        porce.setTitulo(nuevoTitulo);
        porce.setProgreso(nuevoProgreso);
       
        
        servpor.savePorcentaje(porce);
        //reconoce la nueva Persona
        return porce;
        }
    
    @GetMapping("mostrar")
    public Porcentaje findPorcentaje(@PathVariable int id) {
        return servpor.findPorcentaje(id);
    }
    
    @GetMapping("/detail/id")
    public ResponseEntity<Porcentaje> getById(@PathVariable ("id") int id) {
       if (!servpor.existsById(id)){
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
       }
    Porcentaje por = servpor.getOne(id).get();
    return new ResponseEntity(por, HttpStatus.OK);
    }
    
}
