
package com.eve.programacion.controller;

import com.eve.programacion.entity.SoftSkill;
import com.eve.programacion.service.SvSoftSkill;
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
@RequestMapping("softskills")
@CrossOrigin(origins = "http://localhost:4200")
public class CSoftSkill {
    @Autowired SvSoftSkill soft;
     @GetMapping("/traer")
    public List<SoftSkill> getSoftSkill() {
       return soft.getSoftSkill();
    }
    
    @GetMapping("/detail/id")
    public ResponseEntity<SoftSkill> getById(@PathVariable ("id") int id) {
       if (!soft.existsById(id)){
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
       }
       
    SoftSkill softs = soft.getOne(id).get();
    return new ResponseEntity(softs, HttpStatus.OK);
    }
    
    @PostMapping ("/nueva")
    public String createSoftSkill(@RequestBody SoftSkill softs) {
        soft.saveSoftSkill(softs);
        //devuelve un string avisando si creó correctamente
        return "La habilidad fue creada correctamente";
    }
    
    @DeleteMapping ("/borrar/{id}")
    public String deleteSoftSkill (@PathVariable int id){
        soft.deleteSoftSkill(id);
        //devuelve un string avisando que borró correctamente
        return "La habilidad fue borrada correctamente";
    }
    
    @PutMapping ("/editar/{id}")
    public SoftSkill editSoftSkill (@PathVariable int id,
                                @RequestParam("habilidad") String nuevaHabilidad,
                                @RequestParam("imagen") String nuevaImagen,
                                @RequestParam("descripcion") String nuevaDescripcion
                               ) {
        //busco la persona en cuestion
        SoftSkill skill = soft.findSoftSkill(id);
        
        //esto tambien puede ir en un service
        //para desacoplar aún mejor el código en un nuevo método
        skill.setHabilidad(nuevaHabilidad);
        skill.setImagen(nuevaImagen);
        skill.setDescripcion(nuevaDescripcion);
        
        
        soft.saveSoftSkill(skill);
        //reconoce la nueva SoftSkill
        return skill;
        }
     @GetMapping("mostrar")
    public SoftSkill findSoftSkill(@PathVariable int id) {
        return soft.findSoftSkill(id);
    }
    
}

