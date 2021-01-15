package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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
