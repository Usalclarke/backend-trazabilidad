package ar.com.trazabilidad.implementacion;

import ar.com.trazabilidad.datos.ProductosDao;
import ar.com.trazabilidad.dominio.Productos;
import ar.com.trazabilidad.servicio.ProductosService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductosServiceImp implements ProductosService{

    @Autowired
    ProductosDao productodao;
    
    @Override
    @Transactional
    public Productos save(Productos producto) {
        return productodao.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Productos> findAll() {
        return (List<Productos>) productodao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Productos> findById(Integer id) {
        return productodao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return productodao.existsById(id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        productodao.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Productos producto) {
        productodao.delete(producto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Productos> findByCodProducto(String codProducto) {
        return productodao.findByCodProducto(codProducto);
    }
    
}
