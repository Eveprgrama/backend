
package com.eve.programacion.controller;


import com.eve.programacion.entity.SobreMi;
import com.eve.programacion.service.SvSobreMi;
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
@RequestMapping("sobremi")
@CrossOrigin(origins = "http://localhost:4200")
@Transactional
public class CtrlSobreMi {
    @Autowired 
    private SvSobreMi intsm;
    
    @GetMapping("/lista")
    public List<SobreMi> getSobreMi() {
       return intsm.getSobreMi();
    }
    
    @PostMapping ("/nueva")
    public String createSobreMi(@RequestBody SobreMi smi) {
        intsm.saveSobreMi(smi);
        //devuelve un string avisando si creó correctamente
        return "SobreMi fue creado correctamente";
    }
    
    @DeleteMapping ("/borrar/{id}")
    public String deleteSobreMi (@PathVariable int id){
        intsm.deleteSobreMi(id);
        //devuelve un string avisando que borró correctamente
        return "Sobre mí fue borrado correctamente";
    }
    
    @PutMapping ("/editar/{id}")
    public SobreMi editSobreMi (@PathVariable int id,
                                @RequestParam("texto") String nuevoTexto,
                                @RequestParam("imagen") String nuevaImagen)
                                 {
        //busco la experiencia en cuestion
        SobreMi acerca = intsm.findSobreMi(id);
        
        //esto tambien puede ir en un service
        //para desacoplar aún mejor el código en un nuevo método
        acerca.setTexto(nuevoTexto);
        acerca.setImagen(nuevaImagen);
        
        
        intsm.saveSobreMi(acerca);
        //reconoce la nueva Persona
        return acerca;
        }
    
     @GetMapping("/detail/id")
    public ResponseEntity<SobreMi> getById(@PathVariable ("id") int id) {
       if (!intsm.existsById(id)){
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
       }
    SobreMi sobrem = intsm.getOne(id).get();
    return new ResponseEntity(sobrem, HttpStatus.OK);
    }


    @GetMapping("mostrar")
    public SobreMi findSobreMi() {
        return intsm.findSobreMi((int)1);
    }
    }