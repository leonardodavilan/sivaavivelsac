package pe.com.avivel.sistemas.siva.models.entity.produccion;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="vw_sipraone_granja")
public class PrdGranja implements Serializable {

    @Id
    @Column(name="granja_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="granja_codigo")
    private String codigo;

    @Column(name="granja_nombre")
    private String nombre;

    @Column(name="granja_nombrecorto")
    private String nombreCorto;

    @Column(name = "granja_etapa")
    private String granjaEtapa;

    private static final long serialVersionUID = 1L;

}
