package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGranja;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Data
@Table(name = "roe_controlroedor")
public class ControlRoedor implements Serializable {

    @Id
    @Column(name="controlroedor_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="controlroedor_fecha")
    @Temporal(value = TemporalType.DATE)
    private Date fecha;

    @ManyToOne(targetEntity = PrdGranja.class)
    @JoinColumn(name = "granja_id")
    private PrdGranja prdGranja;

    @ManyToOne(targetEntity = ZonaSubZonaControl.class)
    @JoinColumn(name = "zona_subzona_control_id")
    private ZonaSubZonaControl zonaSubZonaControl;

    @JsonIgnoreProperties(value={"controlRoedor", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "controlroedor_id")
    private List<Consumo> consumos;

    private static final long serialVersionUID = 1L;

    public ControlRoedor(){
        consumos = new ArrayList<>();
    }
}
