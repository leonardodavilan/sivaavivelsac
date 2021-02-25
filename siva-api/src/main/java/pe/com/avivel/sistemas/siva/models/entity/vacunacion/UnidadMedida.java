package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Data
@Table(name="vac_unidades_medidas")
public class UnidadMedida implements Serializable {

    @Id
    @Column(name="unidad_medida_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Size(max=5, message="el tamaño tiene que ser como máximo 5 dígitos")
    @Column(name = "unidad_medida_simbolo")
    private String simbolo;

    @Size(max=20, message="el tamaño tiene que ser como máximo 20 dígitos")
    @Column(name = "unidad_medida_nombre")
    private String nombre;

    @Size(max=3, message="el tamaño tiene que ser como máximo 3 dígitos")
    @Column(name = "unidad_medida_codigo")
    private String codigo;

    @Column(name="unidad_medida_estado")
    private int estado;

    private static final long serialVersionUID = 1L;
}
