package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "vac_solicitudes")
public class Solicitud implements Serializable {


    @Id
    @Column(name="solicitud_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="solicitud_codigo")
    private Integer codigo;

    @Column(name="solicitud_fecha")
    @Temporal(value = TemporalType.DATE)
    private Date fecha;

    @Column(name="solicitud_ccosto")
    private String ccosto;

    @Column(name="solicitud_usuario_pedido")
    private String usuarioPedido;

    @ManyToOne(targetEntity = EstadoSolicitud.class)
    @JoinColumn(name = "estado_solicitud_id")
    private EstadoSolicitud estadoSolicitud;

    @JsonIgnoreProperties(value={"solicitud", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "solicitud_id")
    private List<SolicitudItem> items;


    public Solicitud(){
        items = new ArrayList<>();
    }

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal("0.0");;
        for (SolicitudItem item : items) {
            //total += item.getImporte();
            total.add(item.getImporte());
        }
        return total;
    }

    private static final long serialVersionUID = 1L;

}
