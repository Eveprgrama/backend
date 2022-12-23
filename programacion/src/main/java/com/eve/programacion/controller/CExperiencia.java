
package com.eve.programacion.controller;

import com.eve.programacion.entity.Experiencia;
import com.eve.programacion.service.SvExperiencia;
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
@RequestMapping("experiencialaboral")
@CrossOrigin(origins = "http://localhost:4200")
@Transactional
public class CExperiencia {
    @Autowired
    SvExperiencia servexp;
    
    @GetMapping("/lista")
    public List<Experiencia> getExperiencia() {
       return servexp.getExperiencia();
    }
    
    @PostMapping ("/nueva")
    public String createExperiencia(@RequestBody Experiencia exp) {
        servexp.saveExperiencia(exp);
        //devuelve un string avisando si creó correctamente
        return "La Experiencia fue creada correctamente";
    }
    
    @DeleteMapping ("/borrar/{id}")
    public String deleteExperiencia (@PathVariable int id){
        servexp.deleteExperiencia(id);
        //devuelve un string avisando que borró correctamente
        return "La experiencia fue borrada correctamente";
    }
    
    @PutMapping ("/editar/{id}")
    public Experiencia editExperiencia (@PathVariable int id,
                                @RequestParam("nombreE") String nuevoNombreE,
                                @RequestParam("descripcionE") String nuevaDescripcion,
                                @RequestParam("inicio") String nuevoInicio,
                                @RequestParam("fin") String nuevoFin,
                                @RequestParam("localidad") String nuevaLocalidad,
                                @RequestParam("img") String nuevaImagen) {
        //busco la experiencia en cuestion
        Experiencia expe = servexp.findExperiencia(id);
        
        //esto tambien puede ir en un service
        //para desacoplar aún mejor el código en un nuevo método
        expe.setNombreE(nuevoNombreE);
        expe.setDescripcionE(nuevaDescripcion);
        expe.setInicio(nuevoInicio);
        expe.setFin(nuevoFin);
        expe.setLocalidad(nuevaLocalidad);
        expe.setImg(nuevaImagen);
        
        servexp.saveExperiencia(expe);
        //reconoce la nueva Persona
        return expe;
        }
    
     @GetMapping("/detail/id")
    public ResponseEntity<Experiencia> getById(@PathVariable ("id") int id) {
       if (!servexp.existsById(id)){
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
       }
    Experiencia expe = servexp.getOne(id).get();
    return new ResponseEntity(expe, HttpStatus.OK);
    }


    @GetMapping("mostrar")
    public Experiencia findExperiencia(@PathVariable int id) {
        return servexp.findExperiencia(id);
    }
    }
    