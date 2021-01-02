package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdLote;
import pe.com.avivel.sistemas.siva.models.entity.trazabilidad.LoteSerie;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name="vac_vacunaciones")
public class Vacunacion implements Serializable {

    @Id
    @Column(name="vacunacion_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="vacunacion_codigo")
    private String codigo;

    @Column(name="vacunacion_fecha")
    @Temporal(value = TemporalType.DATE)
    private Date fecha;

    @Column(name="vacunacion_observacion")
    private String observacion;

    @ManyToOne(targetEntity = ProgramacionVacuna.class)
    @JoinColumn(name = "programacion_vacuna_id")
    private ProgramacionVacuna programacionVacuna;

    @ManyToOne(targetEntity = PrdLote.class)
    @JoinColumn(name = "lote_id")
    private PrdLote lote;


    @ManyToOne(targetEntity = Proveedor.class)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne(targetEntity = MetodoVacuna.class)
    @JoinColumn(name = "metodo_vacuna_id")
    private MetodoVacuna metodoVacuna;

    @ManyToOne(targetEntity = Empleado.class)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToOne(targetEntity = LoteSerie.class)
    @JoinColumn(name = "loteserie_id")
    private LoteSerie loteSerie;


    @Column(name="vacunacion_estado")
    private int estado;

    private static final long serialVersionUID = 1L;





}
