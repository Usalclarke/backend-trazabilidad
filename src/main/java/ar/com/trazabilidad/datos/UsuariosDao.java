package ar.com.trazabilidad.datos;

import org.springframework.data.repository.CrudRepository;
import ar.com.trazabilidad.dominio.Usuarios;
import java.util.Optional;

public interface UsuariosDao extends CrudRepository<Usuarios, Integer>{
    
    public abstract Optional<Usuarios> findByDni(Integer dni);

}
