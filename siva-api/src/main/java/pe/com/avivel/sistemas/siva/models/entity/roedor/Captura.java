package pe.com.avivel.sistemas.siva.models.entity.roedor;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGranja;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "roe_capturas")
public class Captura implements Serializable {

    @Id
    @Column(name="captura_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="captura_fecha")
    private Date fecha;

    @Column(name="captura_numero_zona_control")
    private int numZonaControl;

    @Column(name="captura_numero_material")
    private int numMaterial;

    @Column(name="captura_numeros_capturas")
    private int numCapturas;

    @ManyToOne(targetEntity = PrdGranja.class)
    @JoinColumn(name = "granja_id")
    private PrdGranja prdGranja;

    @ManyToOne(targetEntity = ZonaControl.class)
    @JoinColumn(name = "zona_control_id")
    private ZonaControl zonaControl;

    @ManyToOne(targetEntity = AreaCaptura.class)
    @JoinColumn(name = "area_captura_id")
    private AreaCaptura areaCaptura;

    @ManyToOne(targetEntity = Edad.class)
    @JoinColumn(name = "edad_id")
    private Edad edad;

    @ManyToOne(targetEntity = Sexo.class)
    @JoinColumn(name = "sexo_id")
    private Sexo sexo;

    @Column(name="captura_estado")
    private int estado;

    private static final long serialVersionUID = 1L;

}
