package ar.com.trazabilidad.servicio;

import java.util.List;
import java.util.Optional;
import ar.com.trazabilidad.dominio.Usuarios;

public interface UsuariosService {
    
    public Usuarios save(Usuarios usuario);
    public List<Usuarios> findAll();
    public Optional<Usuarios> findById(Integer id);
    public Optional<Usuarios> findByDni(Integer id);
    public boolean existsById(Integer id);
    public void deleteById(Integer id);
    public void delete(Usuarios usuario);
}
