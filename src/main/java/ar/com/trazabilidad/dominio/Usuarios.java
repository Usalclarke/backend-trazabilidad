package ar.com.trazabilidad.dominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idusuario;
    private String nombre;
    private String apellido;
    @Column(unique = true, nullable = false, length = 8)
    private Integer dni;
    @Column(nullable = false)
    private String cargo;
    @Column(nullable = false)
    private String password;
    private Integer galpon;

}
