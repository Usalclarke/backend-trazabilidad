package ar.com.trazabilidad.servicio;

import ar.com.trazabilidad.dominio.Pedidos;
import java.util.List;
import java.util.Optional;

public interface PedidosService {
    public Pedidos save(Pedidos pedido);
    public List<Pedidos> saveAll(List<Pedidos> pedidos);
    public List<Pedidos> findAll();
    public Optional<Pedidos> findById(Integer id);
    public boolean existsById(Integer id);
    public void deleteById(Integer id);
    public void delete(Pedidos pedido);
}
