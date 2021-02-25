package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.siva.models.entity.auditoria.Auditable;
import pe.com.avivel.sistemas.siva.models.dto.TotalQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGranja;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Audited
@Entity
@Data
@Table(name = "vac_movimientos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Movimiento  extends Auditable<String> {

    private static final long serialVersionUID = -2360688521527606158L;

    @Id
    @Column(name="movimiento_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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

    public  TotalQueryDTO totalQueryDTO(){
        TotalQueryDTO totalQueryDTO = new TotalQueryDTO();
        totalQueryDTO.setTotal(insumoProveedor.getPrecio().doubleValue() * cantidad.doubleValue());
        totalQueryDTO.setMoneda(insumoProveedor.getMoneda().getSimbolo());
        totalQueryDTO.setMonedaCodigo(insumoProveedor.getMoneda().getCodigo());
        return totalQueryDTO;
    }
}
