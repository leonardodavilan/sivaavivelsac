package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.io.Serializable;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Data
@Table(name = "roe_edades")
public class Edad implements Serializable {

    @Id
    @Column(name="edad_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="edad_nombre")
    private String nombre;

    @Column(name="edad_estado")
    private int estado;

    private static final long serialVersionUID = 1L;


}
