package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "vac_solicitudes_items")
public class SolicitudItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solicitud_item_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "insumo_proveedor_id")
    private InsumoProveedor insumoProveedor;

    @Column(name = "solicitud_item_aplicacion")
    private String aplicacion;

    private BigDecimal cantidad;

    @Column(name = "insumo_precio_ref")
    private BigDecimal precioRef;

    @JsonIgnoreProperties(value={"items", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    public Double getImporte() {

        return cantidad.doubleValue() * insumoProveedor.getPrecio().doubleValue();
    }
    private static final long serialVersionUID = 1L;
}
