package ar.com.trazabilidad.implementacion;

import ar.com.trazabilidad.datos.ObservacionesDao;
import ar.com.trazabilidad.dominio.Observaciones;
import ar.com.trazabilidad.dominio.Pedidos;
import ar.com.trazabilidad.dominio.Productos;
import ar.com.trazabilidad.servicio.ObservacionesService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObservacionesServiceImp implements ObservacionesService{

    @Autowired
    ObservacionesDao obsDao;
    
    @Override
    @Transactional
    public Observaciones save(Observaciones obs) {
        return obsDao.save(obs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Observaciones> findAll() {
        return (List<Observaciones>) obsDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Observaciones> findById(Integer id) {
        return obsDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return obsDao.existsById(id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        obsDao.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Observaciones obs) {
        obsDao.delete(obs);
    }
    
    @Override
    public List<Observaciones> findByIdpedidoAndIdproducto(Pedidos pedido, Productos producto){
        return obsDao.findByIdpedidoAndIdproducto(pedido, producto);
    }
    
}
