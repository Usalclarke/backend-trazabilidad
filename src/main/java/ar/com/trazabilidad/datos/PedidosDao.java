package ar.com.trazabilidad.datos;

import org.springframework.data.repository.CrudRepository;
import ar.com.trazabilidad.dominio.Pedidos;
import java.util.List;

public interface PedidosDao extends CrudRepository<Pedidos, Integer> {

    public abstract List<Pedidos> findAllByGalpon(Integer galpon);

}
