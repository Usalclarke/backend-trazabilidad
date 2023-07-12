package ar.com.trazabilidad.webservice;

import ar.com.trazabilidad.dominio.Productos;
import ar.com.trazabilidad.servicio.ProductosService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/productos")
@CrossOrigin
@Slf4j
public class ProductosRS {
    @Autowired
    ProductosService service;

    @GetMapping("/")
    public List<Productos> obtener(){
        return service.findAll();
    }
             
    @GetMapping("/id/{id}")
    public ResponseEntity obtenerPorId(@PathVariable("id") Integer id){
        Optional<Productos> prod = service.findById(id);
        if(!prod.isPresent()){
            throw new ResponseStatusException(
             HttpStatus.NOT_FOUND, "No se encontro un usuario con el ID proporcionado"
        );
        }
        return ResponseEntity.of(prod);                
                   
    }
    @PostMapping("/")
    public Productos crearProducto(@RequestBody Productos prod){
        return service.save(prod);
    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity borrarPorId(@PathVariable("id") Integer id){
        Optional<Productos> prod = service.findById(id);
        if(!prod.isPresent()){
            throw new ResponseStatusException(
             HttpStatus.NOT_FOUND, "No se encontro un producto para borrar con el ID proporcionado"
        );
        }
        service.deleteById(id);
        return ResponseEntity.ok("Se borro correctamente");                
                   
    }
}
