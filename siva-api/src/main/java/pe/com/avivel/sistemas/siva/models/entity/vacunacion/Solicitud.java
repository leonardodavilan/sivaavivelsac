package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "vac_solicitudes")
public class Solicitud implements Serializable {

    @Id
    @Column(name="solicitud_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="solicitud_codigo")
    private String codigo;

    @Column(name="solicitud_fecha")
    private Date fecha;

    @Column(name="solicitud_ccosto")
    private String ccosto;

    @Column(name="solicitud_cantidad")
    private int cantidad;

    @Column(name="solicitud_costo")
    private BigDecimal costo;


    @ManyToOne(targetEntity = EstadoSolicitud.class)
    @JoinColumn(name = "estado_solicitud_id")
    private EstadoSolicitud estadoSolicitud;


    @ManyToOne(targetEntity = Proveedor.class)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;


    @ManyToOne(targetEntity = Insumo.class)
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;


    @ManyToOne(targetEntity = Empleado.class)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    private static final long serialVersionUID = 1L;
}
