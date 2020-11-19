package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Data
@Table(name="vac_monedas")
public class Moneda implements Serializable {

    @Id
    @Column(name="moneda_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Size(max=5, message="el tamaño tiene que ser como máximo 5 dígitos")
    @Column(name = "moneda_codigo")
    private String codigo;

    @Size(max=20, message="el tamaño tiene que ser como máximo 20 dígitos")
    @Column(name = "moneda_simbolo")
    private String simbolo;

    @Size(max=3, message="el tamaño tiene que ser como máximo 3 dígitos")
    @Column(name="moneda_descripcion")
    private String descripcion;

    @Column(name="moneda_estado")
    private int estado;

    private static final long serialVersionUID = 1L;
}
