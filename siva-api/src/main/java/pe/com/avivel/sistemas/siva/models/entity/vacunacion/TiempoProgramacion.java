package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.io.Serializable;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Data
@Table(name = "vac_tiempos_programaciones")
public class TiempoProgramacion implements Serializable {

    @Id
    @Column(name="tiempo_programacion_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="tiempo_programacion_nombre")
    private String nombre;

    @Column(name="tiempo_programacion_estado")
    private int estado;

    private static final long serialVersionUID = 1L;


}
