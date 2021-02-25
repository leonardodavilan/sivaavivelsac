package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.siva.models.entity.auditoria.Auditable;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdLote;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Audited
@Entity
@Data
@Table(name="vac_vacunaciones")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Vacunacion extends Auditable<String> {

    @Id
    @Column(name="vacunacion_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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


    //
    @ManyToOne(targetEntity = InsumoPresentacion.class)
    @JoinColumn(name = "insumo_presentacion_id")
    private InsumoPresentacion insumoPresentacion;
    //

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

    private static final long serialVersionUID = -2360688521527675438L;

}
