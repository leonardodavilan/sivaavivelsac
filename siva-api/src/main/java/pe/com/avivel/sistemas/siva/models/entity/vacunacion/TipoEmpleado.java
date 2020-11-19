package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@Table(name = "vac_tipos_empleados")
public class TipoEmpleado implements Serializable {

    @Id
    @Column(name="tipo_empleado_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="tipo_empleado_nombre")
    private String nombre;

    @Column(name="tipo_empleado_estado")
    private int estado;

    private static final long serialVersionUID = 1L;

}
