package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@Table(name = "roe_sexos")
public class Sexo implements Serializable {

    @Id
    @Column(name="sexo_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="sexo_nombre")
    private String nombre;

    @Column(name="sexo_estado")
    private int estado;

    private static final long serialVersionUID = 1L;


}
