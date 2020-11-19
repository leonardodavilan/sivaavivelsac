package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@Table(name="vac_insumos")
public class Insumo implements Serializable {

    @Id
    @Column(name="insumo_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Size(max=100, message="el tamaño tiene que ser como máximo 100 dígitos")
    @Column(name = "insumo_descripcion")
    private String descripcion;

    @ManyToOne(targetEntity = SubFamilia.class)
    @JoinColumn(name = "subfamilia_id")
    private SubFamilia subFamilia;

    @Column(name="insumo_estado")
    private int estado;

    private static final long serialVersionUID = 1L;



}
