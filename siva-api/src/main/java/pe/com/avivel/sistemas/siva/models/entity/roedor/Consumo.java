package pe.com.avivel.sistemas.siva.models.entity.roedor;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGranja;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Insumo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Data
@Table(name = "roe_consumos")
public class Consumo implements Serializable {

    @Id
    @Column(name="consumo_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="consumo_fecha")
    private Date fecha;

    @Column(name="consumo_total_cods_materiales")
    private int totalCodsMateriales;

    @Column(name="consumo_numero_zonacontrol")
    private int numZonaControl;

    @Column(name="consumo_numeros_inoperativos")
    private int numsInoperativos;

    @Column(name="consumo_numero_material")
    private int numMaterial;

    @ManyToOne(targetEntity = PrdGranja.class)
    @JoinColumn(name = "granja_id")
    private PrdGranja prdGranja;

    @ManyToOne(targetEntity = Insumo.class)
    @JoinColumn(name = "insumo_id")
    private Insumo insumoMaterial;

    @ManyToOne(targetEntity = Insumo.class)
    @JoinColumn(name = "insumo_id", insertable=false, updatable=false)
    private Insumo insumoRodenticida;

    @ManyToOne(targetEntity = ZonaControl.class)
    @JoinColumn(name = "zona_control_id")
    private ZonaControl zonaControl;

    @ManyToOne(targetEntity = TipoCebo.class)
    @JoinColumn(name = "tipo_cebo_id")
    private TipoCebo tipoCebo;

    @Column(name="consumo_estado")
    private int estado;

    private static final long serialVersionUID = 1L;
}
