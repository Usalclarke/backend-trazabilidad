package ar.com.trazabilidad.servicio;

import java.util.List;
import java.util.Optional;
import ar.com.trazabilidad.dominio.Observaciones;
import ar.com.trazabilidad.dominio.Pedidos;
import ar.com.trazabilidad.dominio.Productos;

public interface ObservacionesService {

    public Observaciones save(Observaciones obs);
    public List<Observaciones> findAll();
    public Optional<Observaciones> findById(Integer id);
    public boolean existsById(Integer id);
    public void deleteById(Integer id);
    public void delete(Observaciones obs);
    public List<Observaciones> findByIdpedidoAndIdproducto(Pedidos pedido, Productos producto);
}
