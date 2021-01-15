package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGranja;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "roe_controlroedor")
public class ControlRoedor implements Serializable {

    @Id
    @Column(name="controlroedor_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="controlroedor_fecha")
    @Temporal(value = TemporalType.DATE)
    private Date fecha;

    @ManyToOne(targetEntity = PrdGranja.class)
    @JoinColumn(name = "granja_id")
    private PrdGranja prdGranja;

    @ManyToOne(targetEntity = Insumo.class)
    @JoinColumn(name = "insumo_material_id")
    private Insumo insumoMaterial;

    @ManyToOne(targetEntity = Insumo.class)
    @JoinColumn(name = "insumo_rodenticida_id")
    private Insumo insumoRodenticida;

    @ManyToOne(targetEntity = ZonaSubZonaControl.class)
    @JoinColumn(name = "zona_subzona_control_id")
    private ZonaSubZonaControl zonaSubZonaControl;


    @JsonIgnoreProperties(value={"controlRoedores", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "controlroedor_id")
    private List<Consumo> consumos;

    public ControlRoedor(){
        consumos = new ArrayList<>();
    }

    private static final long serialVersionUID = 1L;

}
