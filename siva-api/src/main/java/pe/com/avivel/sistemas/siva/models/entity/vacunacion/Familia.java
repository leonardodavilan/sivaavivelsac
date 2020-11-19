package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@Table(name="vac_familias")
public class Familia implements Serializable {

    @Id
    @Column(name="familia_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Size(max=6, message="el tamaño tiene que ser como máximo 6 dígitos")
    @Column(name = "familia_codigo")
    private String codigo;

    @Size(max=6, message="el tamaño tiene que ser como máximo 6 dígitos")
    @Column(name = "familia_descripcion")
    private String descripcion;

    private static final long serialVersionUID = 1L;
}
