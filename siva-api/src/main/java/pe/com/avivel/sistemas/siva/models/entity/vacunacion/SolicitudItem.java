package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import pe.com.avivel.sistemas.siva.models.entity.auditoria.Auditable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Audited
@Entity
@Data
@Table(name = "vac_solicitudes_items")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class SolicitudItem extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solicitud_item_id")
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "insumo_proveedor_id")
    private InsumoProveedor insumoProveedor;

    @Column(name = "solicitud_item_aplicacion")
    private String aplicacion;

    private BigDecimal cantidad;

    @Column(name = "insumo_precio_pedido")
    private BigDecimal precioPedido;

    @Column(name = "insumo_moneda_pedido")
    private String monedaPedido;

    @JsonIgnoreProperties(value={"items", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    public BigDecimal getImporte() {
        return cantidad.multiply(insumoProveedor.getPrecio());
    }
    private static final long serialVersionUID = 1L;
}
