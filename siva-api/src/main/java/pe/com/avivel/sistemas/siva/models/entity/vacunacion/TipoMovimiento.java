package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.io.Serializable;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Data
@Table(name="vac_tipos_movimientos")
public class TipoMovimiento implements Serializable {

    @Id
    @Column(name="tipo_movimiento_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo_movimiento_nombre")
    private String nombre;

    @Column(name="tipo_movimiento_estado")
    private int estado;


    private static final long serialVersionUID = 1L;

}