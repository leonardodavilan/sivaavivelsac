package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.siva.models.entity.auditoria.Auditable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Audited
@Entity
@Data
@Table(name="vac_insumos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Insumo extends Auditable<String> {

    @Id
    @Column(name="insumo_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Size(max=100, message="el tamaño tiene que ser como máximo 100 dígitos")
    @Column(name = "insumo_descripcion")
    private String descripcion;

    @ManyToOne(targetEntity = SubFamilia.class)
    @JoinColumn(name = "subfamilia_id")
    private SubFamilia subFamilia;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="vac_insumos_presentaciones", joinColumns= @JoinColumn(name="insumo_id"),
            inverseJoinColumns=@JoinColumn(name="presentacion_id"),
            uniqueConstraints= {@UniqueConstraint(columnNames= {"presentacion_id", "insumo_id"})})
    private List<Presentacion> itemsPresentaciones;

    @Column(name="insumo_estado")
    private int estado;

    private static final long serialVersionUID = 1L;



}
