package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "roe_tipos_cebos")
public class TipoCebo implements Serializable {

    @Id
    @Column(name="tipo_cebo_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="tipo_cebo_nombre")
    private String nombre;

    @Column(name="tipo_cebo_estado")
    private int estado;

    private static final long serialVersionUID = 1L;

}
