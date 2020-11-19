package pe.com.avivel.sistemas.siva.models.entity.roedor;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "roe_subzona_control")
public class SubZonaControl implements Serializable {

    @Id
    @Column(name="subzona_control_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="subzona_control_codigo")
    private String codigo;

    @Column(name="subzona_control_nombre")
    private String nombre;

    @Column(name="subzona_control_estado")
    private int estado;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="roe_zonas_control_subzonas_control", joinColumns= @JoinColumn(name="subzona_control_id"),
            inverseJoinColumns=@JoinColumn(name="zona_control_id"),
            uniqueConstraints= {@UniqueConstraint(columnNames= {"zona_control_id", "subzona_control_id"})})
    private List<ZonaControl> zonaControls;

    private static final long serialVersionUID = 1L;
}
