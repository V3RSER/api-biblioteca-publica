package co.com.sofka.app.biblioteca.controllers;

import co.com.sofka.app.biblioteca.dtos.RecursoDTO;
import co.com.sofka.app.biblioteca.services.iRecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RecursoController {
    @Autowired
    private iRecursoService service;

    // POST
    @PostMapping("/recurso")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RecursoDTO> add(@RequestBody RecursoDTO recursoDTO) {
        return new ResponseEntity<>(this.service.add(recursoDTO), HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/recurso/{id}")
    public ResponseEntity<RecursoDTO> delete(@PathVariable("id") String id) {
        return this.service.findById(id).isEmpty() ?
                new ResponseEntity<>(new RecursoDTO(), HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(this.service.delete(id).get(), HttpStatus.OK);
    }

    // PUT
    @PutMapping("/recurso/{id}")
    public ResponseEntity<RecursoDTO> update(@PathVariable("id") String id,
                                             @RequestBody RecursoDTO recursoDTO) {
        return this.service.findById(id).isEmpty() ?
                new ResponseEntity<>(new RecursoDTO(), HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(this.service.update(id, recursoDTO).get(), HttpStatus.OK);
    }

    @PutMapping("/recurso/{id}/prestar")
    public ResponseEntity<String> lend(@PathVariable("id") String id) {
        return this.service.findById(id).isEmpty() ?
                new ResponseEntity<>("El recurso no existe", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(this.service.lend(id).get(), HttpStatus.OK);

    }

    @PutMapping("/recurso/{id}/devolver")
    public ResponseEntity<String> giveBack(@PathVariable("id") String id) {
        return this.service.findById(id).isEmpty() ?
                new ResponseEntity<>("El recurso no existe", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(this.service.giveBack(id).get(), HttpStatus.OK);
    }

    // GET
    @GetMapping("/recurso/{id}")
    public ResponseEntity<RecursoDTO> findById(@PathVariable("id") String id) {
        return this.service.findById(id).isEmpty() ?
                new ResponseEntity<>(new RecursoDTO(), HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(this.service.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/recurso/tipo/{tipo}")
    public ResponseEntity<List<RecursoDTO>> findByTipo(@PathVariable("tipo") String tipo) {
        return new ResponseEntity<>(this.service.findByTipo(tipo), HttpStatus.OK);
    }

    @GetMapping("/recurso/area/{area}")
    public ResponseEntity<List<RecursoDTO>> findByArea(@PathVariable("area") String area) {
        return new ResponseEntity<>(this.service.findByArea(area), HttpStatus.OK);
    }

    @GetMapping("/recurso/tipo/{tipo}/area/{area}")
    public ResponseEntity<List<RecursoDTO>> findByTipoAndArea(@PathVariable("tipo") String tipo,
                                                              @PathVariable("area") String area) {
        return new ResponseEntity<>(this.service.findByTipoAndArea(tipo, area), HttpStatus.OK);
    }

    @GetMapping("/recursos")
    public ResponseEntity<List<RecursoDTO>> findAll() {
        return new ResponseEntity<>(this.service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/recurso/{id}/disponible")
    public ResponseEntity<String> available(@PathVariable("id") String id) {
        return this.service.findById(id).isEmpty() ?
                new ResponseEntity<>("El recurso no existe", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(this.service.available(id).get(), HttpStatus.OK);
    }
}
