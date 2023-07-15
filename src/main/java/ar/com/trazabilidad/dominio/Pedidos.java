package ar.com.trazabilidad.dominio;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
// import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpedido;
    @Column(unique = true, nullable = false)
    private String codPedido;
    private String descripcion;
    private String cliente;
    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProduccion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTerminado;
    private String vendedor;
    private String direccion;
    private Integer galpon;
    @OneToMany(mappedBy = "idpedido")
    private List<PedidoDetalle> pedidoDetalleList;

    public Pedidos(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public Pedidos(Integer idpedido, String codPedido) {
        this.idpedido = idpedido;
        this.codPedido = codPedido;
    }

    public Integer getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public String getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(String codPedido) {
        this.codPedido = codPedido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaProduccion() {
        return fechaProduccion;
    }

    public void setFechaProduccion(Date fechaProduccion) {
        this.fechaProduccion = fechaProduccion;
    }

    public Date getFechaTerminado() {
        return fechaTerminado;
    }

    public void setFechaTerminado(Date fechaTerminado) {
        this.fechaTerminado = fechaTerminado;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getGalpon() {
        return galpon;
    }

    public void setGalpon(Integer galpon) {
        this.galpon = galpon;
    }

    @JsonManagedReference
    public List<PedidoDetalle> getPedidoDetalleList() {
        return pedidoDetalleList;
    }

    public void setPedidoDetalleList(List<PedidoDetalle> pedidoDetalleList) {
        this.pedidoDetalleList = pedidoDetalleList;
    }

    public boolean hasFechaTerminado() {
        return fechaTerminado != null;
    }

}
