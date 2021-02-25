package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.io.Serializable;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Data
@Table(name = "vac_metodos_vacunas")
public class MetodoVacuna implements Serializable {

    @Id
    @Column(name="metodo_vacuna_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="metodo_vacuna_nombre")
    private String nombre;

    @Column(name="metodo_vacuna_estado")
    private int estado;

    private static final long serialVersionUID = 1L;

    public MetodoVacuna setId(Integer id) {
        this.id = id;
        return this;
    }
}
