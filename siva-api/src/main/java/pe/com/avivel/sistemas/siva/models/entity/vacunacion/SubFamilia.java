package pe.com.avivel.sistemas.siva.models.entity.vacunacion;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Data
@Table(name="vac_subfamilias")
public class SubFamilia implements Serializable {

    @Id
    @Column(name="subfamilia_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Size(max=8, message="el tamaño tiene que ser como máximo 8 dígitos")
    @Column(name = "subfamilia_codigo")
    private String codigo;

    @Size(max=100, message="el tamaño tiene que ser como máximo 100 dígitos")
    @Column(name = "subfamilia_descripcion")
    private String descripcion;

    @ManyToOne(targetEntity = Familia.class)
    @JoinColumn(name = "familia_id")
    private Familia familia;

    private static final long serialVersionUID = 1L;


    public SubFamilia setSubFamiliId(Integer subFamiliId) {
        this.id = subFamiliId;
        return this;
    }
}
