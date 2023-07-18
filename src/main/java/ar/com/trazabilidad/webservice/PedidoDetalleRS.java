package ar.com.trazabilidad.webservice;

import ar.com.trazabilidad.dominio.PedidoDetalle;
import ar.com.trazabilidad.dominio.Pedidos;
import ar.com.trazabilidad.dominio.Productos;
import ar.com.trazabilidad.servicio.PedidoDetalleService;
import ar.com.trazabilidad.servicio.PedidosService;
import ar.com.trazabilidad.servicio.ProductosService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/pedidoDetalle")
@CrossOrigin
@Slf4j
public class PedidoDetalleRS {

    @Autowired
    PedidoDetalleService service;

    @Autowired
    ProductosService serviceProducto;

    @Autowired
    PedidosService servicePedido;

    @GetMapping("/")
    public List<PedidoDetalle> obtener() {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity obtenerPorId(@PathVariable("id") Integer id) {
        Optional<PedidoDetalle> pedido = service.findById(id);
        if (!pedido.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontro un detalle de pedido con el ID proporcionado"
            );
        }
        return ResponseEntity.of(pedido);

    }

    @GetMapping("/idpedido/{id}")
    public List<PedidoDetalle> obtenerPorIdPedido(@PathVariable("id") Integer id) {
        List<PedidoDetalle> detalles = service.findAll();
        List<PedidoDetalle> detallesfiltrado = new ArrayList<PedidoDetalle>();
        for (PedidoDetalle detalle : detalles) {
            System.out.println(detalle.getIdpedido().getIdpedido());
            if (detalle.getIdpedido().getIdpedido() == id) {
                detallesfiltrado.add(detalle);
            }
        }
        System.out.println("tamanio:" + detallesfiltrado.size());
        return detallesfiltrado;
    }

    @PostMapping("/")
    public PedidoDetalle crearPedidoDetalle(@RequestBody PedidoDetalle pedido) {
        return service.save(pedido);
    }

    @PostMapping("/all")
    public ResponseEntity crearPedidoDetalles(@RequestBody List<PedidoDetalle> detalles) {

        for (PedidoDetalle detalle : detalles) {
            //EXTRAER CODIGO DE PRODUCTO
            String codProducto = detalle.getCodProducto();
            //BUSCAR PRODUCTO 
            Optional<Productos> producto = serviceProducto.findByCodProducto(codProducto);
            if (producto.isPresent()) {
                detalle.setIdproducto(producto.get());
            } else {
                Productos nuevoProducto = new Productos(detalle.getCodProducto(), detalle.getDescripcion(), 1000);
                nuevoProducto = serviceProducto.save(nuevoProducto);
                detalle.setIdproducto(nuevoProducto);
            }
            service.save(detalle);
        }
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity borrarPorId(@PathVariable("id") Integer id) {
        Optional<PedidoDetalle> pedido = service.findById(id);
        if (!pedido.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontro un detalle de pedido para borrar con el ID proporcionado"
            );
        }
        service.deleteById(id);
        return ResponseEntity.ok("Se borro correctamente");
    }

}
