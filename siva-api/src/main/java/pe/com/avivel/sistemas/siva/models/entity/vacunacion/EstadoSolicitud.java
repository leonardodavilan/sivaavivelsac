package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.io.Serializable;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Data
@Table(name = "vac_estados_solicitudes")
public class EstadoSolicitud implements Serializable {

    @Id
    @Column(name="estado_solicitud_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="estado_solicitud_nombre")
    private String nombre;

    @Column(name="estado_solicitud_estado")
    private int estado;

    private static final long serialVersionUID = 1L;
}
