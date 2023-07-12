package ar.com.trazabilidad.datos;

import org.springframework.data.repository.CrudRepository;
import ar.com.trazabilidad.dominio.Productos;
import java.util.Optional;

public interface ProductosDao extends CrudRepository<Productos, Integer> {

    public abstract Optional<Productos> findByCodProducto(String cod);

}
