
package com.eve.programacion.controller;


import com.eve.programacion.entity.Persona;
import com.eve.programacion.service.ImpPersonaService;
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
@RequestMapping("personas")
@CrossOrigin(origins = "http://localhost:4200")
@Transactional
public class CtrlPersona {
    @Autowired 
    ImpPersonaService interPersona;
    
    @GetMapping("/traer")
    public List<Persona> getPersona() {
       return interPersona.getPersona();
    }
    
    @GetMapping("/detail/id")
    public ResponseEntity<Persona> getById(@PathVariable ("id") Long id) {
       if (!interPersona.existsById(id)){
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
       }
    Persona perso = interPersona.getOne(id).get();
    return new ResponseEntity(perso, HttpStatus.OK);
    }
    
    @PostMapping ("/nueva")
    public String createPersona(@RequestBody Persona pers) {
        interPersona.savePersona(pers);
        //devuelve un string avisando si creó correctamente
        return "La persona fue creada correctamente";
    }
    
    @DeleteMapping ("/borrar/{id}")
    public String deletePersona (@PathVariable Long id){
        interPersona.deletePersona(id);
        //devuelve un string avisando que borró correctamente
        return "La persona fue borrada correctamente";
    }
    
    @PutMapping ("/editar/{id}")
    public Persona editPersona (@PathVariable Long id,
                                @RequestParam("nombre") String nuevoNombre,
                                @RequestParam("apellido") String nuevoApellido,
                                @RequestParam("titulo") String nuevoTitulo,
                                @RequestParam("img") String nuevaImagen) {
        //busco la persona en cuestion
        Persona perso = interPersona.findPersona(id);
        
        //esto tambien puede ir en un service
        //para desacoplar aún mejor el código en un nuevo método
        perso.setApellido(nuevoApellido);
        perso.setNombre(nuevoNombre);
        perso.setTitulo(nuevoTitulo);
        perso.setImg(nuevaImagen);
        
        interPersona.savePersona(perso);
        //reconoce la nueva Persona
        return perso;
        }
    
    @GetMapping("perfil")
    public Persona findPersona() {
        return interPersona.findPersona((long)1);
    }
}
