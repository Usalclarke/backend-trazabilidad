package ar.com.trazabilidad.implementacion;

import ar.com.trazabilidad.datos.PedidosDao;
import ar.com.trazabilidad.dominio.Pedidos;
import ar.com.trazabilidad.servicio.PedidosService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidosServiceImp implements PedidosService {
    
    @Autowired
    PedidosDao pedidosdao;
    
    @Override
    @Transactional
    public Pedidos save(Pedidos pedido) {
        return pedidosdao.save(pedido);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Pedidos> findAll() {
        return (List<Pedidos>) pedidosdao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Pedidos> findById(Integer id) {
        return pedidosdao.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return pedidosdao.existsById(id);
    }
    
    @Override
    @Transactional
    public void deleteById(Integer id) {
        pedidosdao.deleteById(id);
    }
    
    @Override
    @Transactional
    public void delete(Pedidos pedido) {
        pedidosdao.delete(pedido);
    }
    
    @Override
    public List<Pedidos> saveAll(List<Pedidos> pedidos) {
        return (List<Pedidos>) pedidosdao.saveAll(pedidos);
    }
    
    @Override
    public List<Pedidos> findAllByGalpon(Integer galpon) {
        return (List<Pedidos>) pedidosdao.findAllByGalpon(galpon);
    }
    
}
