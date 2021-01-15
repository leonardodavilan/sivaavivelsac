package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "roe_consumos")
public class Consumo implements Serializable {

    @Id
    @Column(name="consumo_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="consumo_numero_zonacontrol")
    private int numZonaControl;

    @Column(name="consumo_total_cods_materiales")
    private int totalCodsMateriales;

    @Column(name="consumo_numeros_inoperativos")
    private int numsInoperativos;

    @Column(name="consumo_numero_material")
    private int numMaterial;

    @ManyToOne(targetEntity = TipoCebo.class)
    @JoinColumn(name = "tipo_cebo_id")
    private TipoCebo tipoCebo;

    @Column(name="consumo_estado")
    private int estado;

    @JsonIgnoreProperties(value={"consumos", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "controlroedor_id")
    private ControlRoedor controlRoedores;

    @JsonIgnoreProperties(value={"consumos", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "consumo_id")
    private List<Captura> capturas;

    public Consumo(){
        capturas = new ArrayList<>();
    }

    private static final long serialVersionUID = 1L;
}
