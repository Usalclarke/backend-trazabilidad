package ar.com.trazabilidad.dominio;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="observaciones")
public class Observaciones implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idobservacion;
    @Column(unique = true, nullable = false)
    private String codObservacion;
    @Column(nullable = false)
    private String motivo;
    @Column(nullable = false)
    private Integer cantidadPiezas;
    @JoinColumn(name = "idpedido", referencedColumnName = "idpedido")
    @ManyToOne
    private Pedidos idpedido;
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    @ManyToOne
    private Productos idproducto;
}
