package ar.com.trazabilidad.datos;
import org.springframework.data.repository.CrudRepository;
import ar.com.trazabilidad.dominio.PedidoDetalle;
import java.util.List;

public interface PedidoDetalleDao extends CrudRepository<PedidoDetalle, Integer>{
    public List<PedidoDetalle> findAllByIdpedido(Integer id); 
}
