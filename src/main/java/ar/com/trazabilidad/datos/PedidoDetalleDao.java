package ar.com.trazabilidad.datos;
import org.springframework.data.repository.CrudRepository;
import ar.com.trazabilidad.dominio.PedidoDetalle;
import ar.com.trazabilidad.dominio.Productos;
import java.util.List;

public interface PedidoDetalleDao extends CrudRepository<PedidoDetalle, Integer>{
    public List<PedidoDetalle> findAllByIdpedido(Integer id); 
    public List<PedidoDetalle> findAllByIdproducto(Productos producto); 
}
