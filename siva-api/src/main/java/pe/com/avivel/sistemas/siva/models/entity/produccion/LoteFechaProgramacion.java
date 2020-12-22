package pe.com.avivel.sistemas.siva.models.entity.produccion;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.trazabilidad.LoteSerie;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Data
@Table(name = "vw_lote_fechaprogramacion")
public class LoteFechaProgramacion implements Serializable {

    @Id
    @Column(name="id_vista")
    private BigDecimal id;

    @ManyToOne(targetEntity = PrdLote.class)
    @JoinColumn(name = "lote")
    private PrdLote lote;

    @Column(name="lote_id")
    private Integer lote_id;

    @ManyToOne(targetEntity = ProgramacionVacuna.class)
    @JoinColumn(name = "programacion_vacuna_id")
    private ProgramacionVacuna programacionVacuna;

    @ManyToOne(targetEntity = NumeroProgramacion.class)
    @JoinColumn(name = "numero_programacion_id")
    private NumeroProgramacion numeroProgramacion;

    @Column(name="insumo_descripcion")
    private String insumoDescripcion;

    @Column(name="granjaNombre")
    private String granjaNombre;

    @ManyToOne(targetEntity = PrdGranja.class)
    @JoinColumn(name = "granja_id")
    private PrdGranja granja;

    @Column(name="etapa")
    private String etapa;

    @Column(name="edad")
    private Integer edad;

    @Column(name="tiempo")
    private String tiempo;

    @Column(name="fecha_programada")
    @Temporal(value = TemporalType.DATE)
    private Date fechaProgramada;

    @Column(name="fecha_ingreso")
    @Temporal(value = TemporalType.DATE)
    private Date fechaIngreso;

    @Column(name="vacunacion_fecha")
    @Temporal(value = TemporalType.DATE)
    private Date vacunacionFecha;


    @ManyToOne(targetEntity = Vacunacion.class)
    @JoinColumn(name = "vacunacion_id")
    private Vacunacion vacunacion;

    @ManyToOne(targetEntity = MetodoVacuna.class)
    @JoinColumn(name = "metodo_vacuna_id")
    private MetodoVacuna metodoVacuna;

    @ManyToOne(targetEntity = Proveedor.class)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne(targetEntity = LoteSerie.class)
    @JoinColumn(name = "loteserie_id")
    private LoteSerie loteSerie;

    @Column(name="vacunacion_observacion")
    private String observacion;

    private static final long serialVersionUID = 1L;
}
