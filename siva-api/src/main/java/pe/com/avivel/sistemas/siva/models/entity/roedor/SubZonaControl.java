package pe.com.avivel.sistemas.siva.models.entity.roedor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Integer id;

    @Column(name="subzona_control_codigo")
    private String codigo;

    @Column(name="subzona_control_nombre")
    private String nombre;

    @Column(name="subzona_control_estado")
    private int estado;

    @JsonIgnoreProperties(value={"subZonaControls", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="roe_zonas_subzonas_control", joinColumns= @JoinColumn(name="subzona_control_id"),
            inverseJoinColumns=@JoinColumn(name="zona_control_id"),
            uniqueConstraints= {@UniqueConstraint(columnNames= {"zona_control_id", "subzona_control_id"})})
    private List<ZonaControl> zonaControl;

    private static final long serialVersionUID = 1L;
}
