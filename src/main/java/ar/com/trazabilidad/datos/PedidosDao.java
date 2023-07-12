package ar.com.trazabilidad.datos;

import org.springframework.data.repository.CrudRepository;
import ar.com.trazabilidad.dominio.Pedidos;

public interface PedidosDao extends CrudRepository<Pedidos, Integer>{
    
}
