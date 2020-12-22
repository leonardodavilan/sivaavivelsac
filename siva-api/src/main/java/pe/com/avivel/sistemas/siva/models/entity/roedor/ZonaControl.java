package pe.com.avivel.sistemas.siva.models.entity.roedor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "roe_zonas_control")
public class ZonaControl implements Serializable {

    @Id
    @Column(name="zona_control_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="zona_control_codigo")
    private String codigo;

    @Column(name="zona_control_nombre")
    private String nombre;

    @Column(name="zona_control_estado")
    private int estado;

    @JsonIgnoreProperties(value={"zonaControl", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="roe_zonas_subzonas_control", joinColumns= @JoinColumn(name="zona_control_id"),
            inverseJoinColumns=@JoinColumn(name="subzona_control_id"),
            uniqueConstraints= {@UniqueConstraint(columnNames= {"zona_control_id", "subzona_control_id"})})
    private List<SubZonaControl> subZonaControl;

    private static final long serialVersionUID = 1L;
}
