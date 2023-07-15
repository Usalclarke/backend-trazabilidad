package ar.com.trazabilidad.webservice;

import ar.com.trazabilidad.dominio.Observaciones;
import ar.com.trazabilidad.dominio.PedidoDetalle;
import ar.com.trazabilidad.dominio.Pedidos;
import ar.com.trazabilidad.dominio.Productos;
import ar.com.trazabilidad.servicio.ObservacionesService;
import ar.com.trazabilidad.servicio.PedidoDetalleService;
import ar.com.trazabilidad.servicio.PedidosService;
import ar.com.trazabilidad.servicio.ProductosService;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/estadistica")
@CrossOrigin
@Slf4j
public class EstadisticaRS {

    @Autowired
    ObservacionesService ObservacionesService;

    @Autowired
    PedidoDetalleService pedidoDetallService;

    @Autowired
    ProductosService productosService;

    @Autowired
    PedidosService pedidoService;

    @GetMapping("/eficienciaGalpones")
    public ResponseEntity eficienciaGalpon() {

        List<EficienciaGalpon> eficienciagalpon = new ArrayList<EficienciaGalpon>();

        List<Pedidos> pedidos = pedidoService.findAll();

        Map<Integer, List<Pedidos>> pedidosByGalpon = new HashMap<>();
        for (Pedidos pedido : pedidos) {
            Integer galpon = pedido.getGalpon();
            if (galpon != null) {
                pedidosByGalpon.computeIfAbsent(galpon, k -> new ArrayList<>()).add(pedido);
            }
        }
        Integer productosFabricados = 0;
        Integer productosObservados = 0;
        for (Map.Entry<Integer, List<Pedidos>> value : pedidosByGalpon.entrySet()) {
            Integer key = value.getKey();

            List<Pedidos> pedidos_ = value.getValue();

            productosFabricados = pedidos_.stream()
                    .flatMap(pedido -> pedido.getPedidoDetalleList().stream())
                    .mapToInt(PedidoDetalle::getCantidadInteger)
                    .sum();

            for (Pedidos pedido : pedidos_) {
                List<Observaciones> observaciones = ObservacionesService.findByIdpedido(pedido);
                productosObservados += observaciones.stream()
                        .mapToInt(Observaciones::getCantidadPiezas)
                        .sum();
            }
            log.info("Galpon: " + key + ", productosFabricados: " + productosFabricados + ", productosObservados: " + productosObservados);
            eficienciagalpon.add(new EficienciaGalpon(key, productosFabricados, productosObservados));
        }

        return ResponseEntity.ok(eficienciagalpon);
    }

    @GetMapping("/eficienciaProducto/{codProducto}/{galpon}")
    public ResponseEntity eficienciaProducto(@PathVariable("codProducto") String codProducto, @PathVariable("galpon") Integer nroGalpon) {

        Optional<Productos> producto = productosService.findByCodProducto(codProducto);

        if (!producto.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        //EFICIENCIA PROD GENERAL Y POR GALPON
        List<PedidoDetalle> pedidosDetalles = pedidoDetallService.findAllByIdproducto(producto.get());

        //GENERAL
        Integer cantidadFabricadas = 0;
        Integer cantidadObservadas = 0;

        //POR GALPON
        Integer cantidadFabricadasGalpon = 0;
        Integer cantidadObservadasGalpon = 0;

        for (PedidoDetalle pedidoDetalle : pedidosDetalles) {

            Integer galpon = pedidoDetalle.getIdpedido().getGalpon();

            Boolean isTerminado = pedidoDetalle.getIdpedido().hasFechaTerminado();

            if (galpon != null && isTerminado) {
                cantidadFabricadas += pedidoDetalle.getCantidadInteger();

                if (galpon == nroGalpon) {
                    cantidadFabricadasGalpon += pedidoDetalle.getCantidadInteger();
                }
            }

        }

        List<Observaciones> observaciones = ObservacionesService.findByIdproducto(producto.get());

        cantidadObservadas += observaciones.stream()
                .mapToInt(Observaciones::getCantidadPiezas)
                .sum();

        cantidadObservadasGalpon += observaciones.stream()
                .filter(observaciones1 -> observaciones1.getIdpedido().getGalpon().equals(nroGalpon))
                .mapToInt(Observaciones::getCantidadPiezas)
                .sum();
        
        EficienciaGalpon eficienciaByGalpon = new EficienciaGalpon(nroGalpon, cantidadFabricadasGalpon, cantidadObservadasGalpon);
        
        return ResponseEntity.ok(
            new EficienciaProducto(producto.get(),cantidadFabricadas,cantidadObservadas, eficienciaByGalpon)
        );
    }

    class EficienciaGalpon {

        public Integer galpon;
        public Integer cantidadFabricados;
        public Integer cantidadObservados;

        public EficienciaGalpon(Integer galpon, Integer cantidadFabricados, Integer cantidadObservados) {
            this.galpon = galpon;
            this.cantidadFabricados = cantidadFabricados;
            this.cantidadObservados = cantidadObservados;
        }
    }

    class EficienciaProducto {

        public Productos producto;
        public Integer cantidadFabricados;
        public Integer cantidadObservados;
        public EficienciaGalpon eficienciagalpon;

        public EficienciaProducto(Productos producto, Integer cantidadFabricados, Integer cantidadObservados, EficienciaGalpon eficienciagalpon) {
            this.producto = producto;
            this.cantidadFabricados = cantidadFabricados;
            this.cantidadObservados = cantidadObservados;
            this.eficienciagalpon = eficienciagalpon;
        }
    }

}
