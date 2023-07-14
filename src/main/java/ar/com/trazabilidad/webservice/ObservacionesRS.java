package ar.com.trazabilidad.webservice;

import ar.com.trazabilidad.servicio.ObservacionesService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ar.com.trazabilidad.dominio.Observaciones;
import ar.com.trazabilidad.dominio.PedidoDetalle;
import ar.com.trazabilidad.dominio.Pedidos;
import ar.com.trazabilidad.servicio.PedidoDetalleService;
import ar.com.trazabilidad.servicio.PedidosService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/observaciones")
@CrossOrigin
@Slf4j
public class ObservacionesRS {

    @Autowired
    ObservacionesService service;

    @Autowired
    PedidoDetalleService pedidoDetallService;

    @Autowired
    PedidosService pedidoService;

    @GetMapping("/")
    public List<ObservacionesGet> obtener() {
        List<Observaciones> observations = service.findAll();

        List<ObservacionesGet> observacionesGet = new ArrayList<>();
        for (Observaciones observation : observations) {
            observacionesGet.add(new ObservacionesGet(observation));
        }

        return observacionesGet;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity obtenerPorId(@PathVariable("id") Integer id) {
        Optional<Observaciones> obs = service.findById(id);
        if (!obs.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontro una observacion con el ID proporcionado");
        }
        return ResponseEntity.ok(obs);
    }

    @PostMapping("/")
    public ResponseEntity crear(@RequestBody Observaciones obs) {

        Integer idpedido = obs.getIdpedido().getIdpedido();
        Integer idproducto = obs.getIdproducto().getIdproducto();

        //PEDIDO QUE SE HACE LA OBSERVACION
        Optional<Pedidos> pedido = pedidoService.findById(idpedido);

        if (!pedido.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontro una observacion con el ID proporcionado");
        }

        //EXTRAER ITEMS DEL PEDIDO
        List<PedidoDetalle> detalleList = pedido.get().getPedidoDetalleList();

        PedidoDetalle detalle = null;

        for (PedidoDetalle item : detalleList) {
            if (item.getIdproducto().getIdproducto().equals(idproducto)) {
                detalle = item;
                break;
            }
        }

        //CANTIDAD MAXIMA DE UNIDADES DEL PEDIDO
        Integer cantidadMax = detalle.getCantidad().intValue();

        Integer cantidadActual = 0;

        List<Observaciones> observaciones = service.findByIdpedidoAndIdproducto(obs.getIdpedido(), obs.getIdproducto());

        for (Observaciones observacion : observaciones) {
            if (obs.getIdobservacion() != observacion.getIdobservacion()) {
                cantidadActual += observacion.getCantidadPiezas();
            }

        }

        log.info(obs.toString());
        cantidadActual += obs.getCantidadPiezas();
        log.info(cantidadActual.toString());
        if ((cantidadActual) > cantidadMax) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cantidad de piezas ingresada no valida");
        }

        Observaciones newobs = service.save(obs);

        return ResponseEntity.ok(newobs);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity borrarPorId(@PathVariable("id") Integer id) {
        Optional<Observaciones> obs = service.findById(id);
        if (!obs.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontro una observacion para borrar con el ID proporcionado"
            );
        }
        service.deleteById(id);
        return ResponseEntity.ok("Se borro correctamente");
    }

    public class ObservacionesGet {

        public Integer idobservacion;
        public String codObservacion;
        public String codPedido;
        public String codProducto;
        public Integer galpon;
        public String motivo;
        public Integer cantidadPiezas;

        public ObservacionesGet(Observaciones observation) {
            this.idobservacion = observation.getIdobservacion();
            this.codObservacion = observation.getCodObservacion();
            this.motivo = observation.getMotivo();
            this.cantidadPiezas = observation.getCantidadPiezas();
            this.codPedido = observation.getIdpedido().getCodPedido();
            this.galpon = observation.getIdpedido().getGalpon();
            this.codProducto = observation.getIdproducto().getCodProducto();
        }

    }
}
