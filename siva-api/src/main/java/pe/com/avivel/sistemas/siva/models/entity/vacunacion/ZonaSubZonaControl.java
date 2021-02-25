package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.io.Serializable;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Data
@Table(name = "roe_zonas_subzonas_control")
public class ZonaSubZonaControl implements Serializable {

    @Id
    @Column(name="zona_subzona_control_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnoreProperties(value={"subZonaControl", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(targetEntity = ZonaControl.class)
    @JoinColumn(name = "zona_control_id")
    private ZonaControl zonaControl;

    @JsonIgnoreProperties(value={"zonaControl", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(targetEntity = SubZonaControl.class)
    @JoinColumn(name = "subzona_control_id")
    private SubZonaControl subZonaControl;


    private static final long serialVersionUID = 1L;
}
