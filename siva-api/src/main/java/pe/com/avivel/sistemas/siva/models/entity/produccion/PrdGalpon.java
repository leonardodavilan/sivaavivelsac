package pe.com.avivel.sistemas.siva.models.entity.produccion;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@Table(name = "vw_sipraone_galpon")
public class PrdGalpon implements Serializable {

    @Id
    @Column(name="galpon_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "galpon_codigo")
    private String codigo;

    @Column(name = "galpon_capacidad")
    private int capacidad;

    @Column(name = "galpon_capacidad_utilizada")
    private int capacidadUtilizada;

    @Column(name = "galpon_descripcion")
    private String descripcion;

    @ManyToOne(targetEntity = PrdGranja.class)
    @JoinColumn(name = "granja_id")
    private PrdGranja granja;

    private static final long serialVersionUID = 1L;
}
