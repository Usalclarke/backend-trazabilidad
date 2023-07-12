package ar.com.trazabilidad.implementacion;

import ar.com.trazabilidad.datos.PedidoDetalleDao;
import ar.com.trazabilidad.dominio.PedidoDetalle;
import ar.com.trazabilidad.servicio.PedidoDetalleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoDetalleServiceImp implements PedidoDetalleService{

    @Autowired
    PedidoDetalleDao pedidodetalledao;
    
    @Override
    @Transactional
    public PedidoDetalle save(PedidoDetalle pedidodetalle) {
        return pedidodetalledao.save(pedidodetalle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDetalle> findAll() {
        return (List<PedidoDetalle>) pedidodetalledao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PedidoDetalle> findById(Integer id) {
        return pedidodetalledao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return pedidodetalledao.existsById(id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        pedidodetalledao.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(PedidoDetalle pedidodetalle) {
        pedidodetalledao.delete(pedidodetalle);
    }

    @Override
    public List<PedidoDetalle> findAllByIdPedido(Integer id) {
        return pedidodetalledao.findAllByIdpedido(id);
    }

    @Override
    public List<PedidoDetalle> saveAll(List<PedidoDetalle> pedidodetalles) {
        return (List<PedidoDetalle>) pedidodetalledao.saveAll(pedidodetalles);
    }
    
}
