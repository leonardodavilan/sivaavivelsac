package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name="vac_presentaciones")
public class Presentacion {

    @Id
    @Column(name="presentacion_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Size(max=80, message="el tamaño tiene que ser como máximo 80 dígitos")
    @Column(name = "presentacion_nombre")
    private String nombre;

    @Column(name="presentacion_estado")
    private int estado;

    private static final long serialVersionUID = 1L;
}
