package ar.com.trazabilidad.webservice;

import ar.com.trazabilidad.dominio.Usuarios;
import ar.com.trazabilidad.servicio.UsuariosService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin
@Slf4j
public class UsuariosRS {

    @Autowired
    UsuariosService service;

    @GetMapping("/")
    public List<Usuarios> obtenerUsuarios() {

        List<Usuarios> usuarios = service.findAll();
        
        Collections.sort(usuarios, Comparator.comparing(Usuarios::getIdusuario));

        List<Usuarios> usuarios_ = usuarios.subList(1, usuarios.size());

        return usuarios_;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity obtenerUsuarioId(@PathVariable("id") Integer id){
        
        Optional<Usuarios> user = service.findById(id);
        if(!user.isPresent()){
            throw new ResponseStatusException(
             HttpStatus.NOT_FOUND, "No se encontro un usuario con el ID proporcionado"
        );
        }
        return ResponseEntity.of(user);                
                   
    }
    /*@GetMapping("/id/{id}")
    public ResponseEntity<?> obtenerUsuarioId(@PathVariable("id") Integer id) {
        try {
            Optional<Usuarios> user = service.findById(id);
            if (user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro un usuario con el ID proporcionado");
            }
            return ResponseEntity.ok(user.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error al procesar la solicitud: " + e.getMessage());
        }
    }*/

    @PostMapping("/")
    public Usuarios crearUsuario(@RequestBody Usuarios user) {
        return service.save(user);  

    }

    @PostMapping("/validarLogin")
    public ResponseEntity validarLogin(@RequestBody Usuarios user1) {
        //BUSCAR UN USUARIO CON EL DNI OBTENIDO
        Optional<Usuarios> user = service.findByDni(user1.getDni());
        //VERIFICAR SI SE ENCONTRO UN USER CON EL DNI ENVIADO
        if (!user.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "DNI o contraseña incorrecto."
            );
        }
        //VALIDAMOS SI LAS PW COINCIDEN
        if (user.get().getPassword().equals(user1.getPassword())) {
            //SI LAS PW COINCIDEN GENERAMOS TOKEN Y ENVIAMOS
            HashMap<String, String> map = new HashMap<String, String>();
            
            map.put("token", generarToken(user.get()));
            
            return ResponseEntity.ok(map);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "DNI o contraseña incorrecto."
            );
        }
    }

    @RequestMapping("/autenticarUsuario")
    public ResponseEntity usuarioAutenticado(@RequestHeader("x-auth-token") String token) {
        log.info("token: " + token);
        try {
            int idUsuario = Jwts.parser()
                    .setSigningKey("trazabilidadtrazabilidadtrazabilidad")
                    .parseClaimsJws(token)
                    .getBody()
                    .get("id", Integer.class);
            System.out.println("FLAG ID USUARIO RECUPERADO: " + idUsuario);
            Optional<Usuarios> usuario = service.findById(idUsuario);
            return ResponseEntity.ok(usuario);

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "token invalido"
            );
        }

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity borrarPorId(@PathVariable("id") Integer id) {
        Optional<Usuarios> cat = service.findById(id);
        if (!cat.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontro un usuario para borrar con el ID proporcionado"
            );
        }
        service.deleteById(id);
        return ResponseEntity.ok("Se borro correctamente");

    }

    public String generarToken(Usuarios usuario) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(usuario.getNombre() + " " + usuario.getApellido())
                .setExpiration(new Date(System.currentTimeMillis() + 900000))
                .signWith(SignatureAlgorithm.HS256, "trazabilidadtrazabilidadtrazabilidad")
                .claim("id", usuario.getIdusuario())
                .compact();
    }
}
