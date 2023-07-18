package ar.com.trazabilidad.dominio;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
// import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pedidoDetalle")
public class PedidoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpedidoDetalle;
    @Column(precision=10, scale=2, nullable = false)
    private BigDecimal unidades;
    @Column(precision=10, scale=2, nullable = false)
    private BigDecimal cantidad;
    private String codProducto;
    @JoinColumn(name = "idpedido", referencedColumnName = "idpedido")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Pedidos idpedido;
    @ManyToOne
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    private Productos idproducto;
    private String descripcion; 

    @JsonBackReference
    public Pedidos getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Pedidos idpedido) {
        this.idpedido = idpedido;
    }

    public Integer getIdpedidoDetalle() {
        return idpedidoDetalle;
    }

    public void setIdpedidoDetalle(Integer idpedidoDetalle) {
        this.idpedidoDetalle = idpedidoDetalle;
    }

    public BigDecimal getUnidades() {
        return unidades;
    }

    public void setUnidades(BigDecimal unidades) {
        this.unidades = unidades;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }
    public Integer getCantidadInteger() {
        return cantidad.intValue();
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Productos getIdproducto() {
        return idproducto;
    }

    public String getCodProducto(){
        return codProducto;
    }
    
    public void setIdproducto(Productos idproducto) {
        this.idproducto = idproducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
