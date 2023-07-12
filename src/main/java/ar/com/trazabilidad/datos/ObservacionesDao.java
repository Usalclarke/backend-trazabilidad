package ar.com.trazabilidad.datos;

import org.springframework.data.repository.CrudRepository;
import ar.com.trazabilidad.dominio.Observaciones;
import ar.com.trazabilidad.dominio.Pedidos;
import ar.com.trazabilidad.dominio.Productos;
import java.util.List;

public interface ObservacionesDao extends CrudRepository<Observaciones, Integer>{
    List<Observaciones> findByIdpedidoAndIdproducto(Pedidos idpedido, Productos idproducto);
}
