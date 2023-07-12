package ar.com.trazabilidad.implementacion;

import ar.com.trazabilidad.datos.UsuariosDao;
import ar.com.trazabilidad.dominio.Usuarios;
import ar.com.trazabilidad.servicio.UsuariosService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuariosServiceImp implements UsuariosService{
    
    @Autowired
    UsuariosDao userdao;
    
    @Override
    @Transactional
    public Usuarios save(Usuarios usuario) {
       return userdao.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuarios> findAll() {
        return (List<Usuarios>) userdao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuarios> findById(Integer id) {
        return userdao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return userdao.existsById(id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        userdao.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Usuarios usuario) {
        userdao.delete(usuario);
    }

    @Override
    public Optional<Usuarios> findByDni(Integer id) {
        return userdao.findByDni(id);
    }
    
}
