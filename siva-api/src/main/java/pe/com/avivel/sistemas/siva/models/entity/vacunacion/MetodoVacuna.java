package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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
