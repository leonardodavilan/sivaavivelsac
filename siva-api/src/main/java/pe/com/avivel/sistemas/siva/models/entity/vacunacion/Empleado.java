package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.io.Serializable;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Data
@Table(name = "vac_empleados")
public class Empleado implements Serializable {

    @Id
    @Column(name="empleado_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="empleado_nombre")
    private String nombre;

    @ManyToOne(targetEntity = TipoEmpleado.class)
    @JoinColumn(name = "tipo_empleado_id")
    private TipoEmpleado tipoEmpleado;

    @Column(name="empleado_estado")
    private int estado;

    private static final long serialVersionUID = 1L;

    public Empleado setId(Integer id){
        this.id = id;
        return this;
    }


}
