package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGranja;
import pe.com.avivel.sistemas.siva.models.entity.trazabilidad.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "vac_movimientos")
public class Movimiento implements Serializable {

    @Id
    @Column(name="movimiento_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "movimiento_fecha")
    @Temporal(value = TemporalType.DATE)
    private Date fecha;

    @Column(name = "movimiento_cantidad")
    private BigDecimal cantidad;

    @ManyToOne(targetEntity = TipoMovimiento.class)
    @JoinColumn(name = "tipo_movimiento_id")
    private TipoMovimiento tipoMovimiento;

    @ManyToOne(targetEntity = PrdGranja.class)
    @JoinColumn(name = "granja_id")
    private PrdGranja prdGranja;

    @ManyToOne(targetEntity = InsumoProveedor.class)
    @JoinColumn(name = "insumo_proveedor_id")
    private InsumoProveedor insumoProveedor;

    @Column(name="movimiento_estado")
    private int estado;

    @ManyToOne(targetEntity = LoteSerie.class)
    @JoinColumn(name = "loteserie_id")
    private LoteSerie loteSerie;

    @ManyToOne(targetEntity = GuiaFactura.class)
    @JoinColumn(name = "guiafactura_id")
    private GuiaFactura guiaFactura;

    private static final long serialVersionUID = 1L;
}
