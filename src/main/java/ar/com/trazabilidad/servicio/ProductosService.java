package ar.com.trazabilidad.servicio;

import ar.com.trazabilidad.dominio.Productos;
import java.util.List;
import java.util.Optional;

public interface ProductosService {

    public Productos save(Productos producto);

    public List<Productos> findAll();

    public Optional<Productos> findById(Integer id);

    public boolean existsById(Integer id);

    public void deleteById(Integer id);

    public void delete(Productos producto);

    public Optional<Productos> findByCodProducto(String codProducto);

}
