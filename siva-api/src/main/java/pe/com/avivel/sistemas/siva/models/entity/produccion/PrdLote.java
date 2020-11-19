package pe.com.avivel.sistemas.siva.models.entity.produccion;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Data
@Table(name = "vw_sipraone_lote")
public class PrdLote implements Serializable {

    @Id
    @Column(name="lote_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="lote_codigo")
    private String codigo;

    @Column(name="lote_fecha_ingreso")
    private Date fechaIngreso;

    @Column(name="lote_fecha_cierre")
    private Date fechaCierre;

    @Column(name="lote_fecha_ingreso_real")
    private Date fechaIngresoReal;

    @Column(name="lote_numero_inicial_aves")
    private int numeroInicialAves;

    @Column(name="lote_activo")
    private int loteActivo;

    @ManyToOne(targetEntity = PrdGalpon.class)
    @JoinColumn(name = "galpon_id")
    private PrdGalpon galpon;

    @ManyToOne(targetEntity = PrdLote.class)
    @JoinColumn(name = "lote_parent_id")
    private PrdLote prdLoteParent;

    @Column(name="lote_etapa")
    private String loteEtapa;

    @Column(name="linea_genetica_nombre")
    private String lineaGeneticaNombre;

    @Column(name="linea_genetica_nombrecorto")
    private String lineaGeneticaNombreCorto;

    @Column(name="lote_fecha_inicio_produccion")
    private Date fechaInicioProduccion;

    private static final long serialVersionUID = 1L;

    public PrdLote setId(Integer loteId) {
        this.id = loteId;
        return this;
    }

}
