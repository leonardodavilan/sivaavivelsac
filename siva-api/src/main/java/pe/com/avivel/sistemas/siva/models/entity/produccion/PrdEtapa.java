package pe.com.avivel.sistemas.siva.models.entity.produccion;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.io.Serializable;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Data
@Table(name="prd_etapas")
public class PrdEtapa implements Serializable {

    @Id
    @Column(name="etapa_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="etapa_nombre")
    private String nombre;

    private static final long serialVersionUID = 1L;
}
