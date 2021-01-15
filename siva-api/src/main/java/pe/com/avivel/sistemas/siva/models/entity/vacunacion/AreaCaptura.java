package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@Table(name = "roe_areas_captura")
public class AreaCaptura implements Serializable {

    @Id
    @Column(name="area_captura_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="area_captura_nombre")
    private String nombre;

    @Column(name="area_captura_estado")
    private int estado;

    private static final long serialVersionUID = 1L;

}
