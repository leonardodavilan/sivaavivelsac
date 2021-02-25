package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import pe.com.avivel.sistemas.siva.models.entity.auditoria.Auditable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Audited
@Entity
@Data
@Table(name = "roe_consumos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Consumo extends Auditable<String> {

    @Id
    @Column(name="consumo_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne(targetEntity = Insumo.class)
    @JoinColumn(name = "consumo_insumo_material_id")
    private Insumo insumoMaterial;

    @ManyToOne(targetEntity = Insumo.class)
    @JoinColumn(name = "consumo_insumo_rodenticida_id")
    private Insumo insumoRodenticida;

    @Column(name="consumo_cant_uso_rodenticida")
    private BigDecimal cantidadUsoRodenticida;

    @ManyToOne(targetEntity = UnidadMedida.class)
    @JoinColumn(name = "consumo_unidad_medida_id")
    private  UnidadMedida unidadMedida;

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

    @JsonIgnoreProperties(value={"consumo", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(targetEntity = ControlRoedor.class)
    @JoinColumn(name = "controlroedor_id")
    private ControlRoedor controlRoedor;

    @JsonIgnoreProperties(value={"consumo", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "consumo_id")
    private List<Captura> capturas;

    public Consumo(){
        capturas = new ArrayList<>();
    }
    public String getGranjaControl(){
        if(controlRoedor != null){
            return controlRoedor.getPrdGranja().getNombre();
        }
        return null;
    }
    public Date getFechaControl(){
        if(controlRoedor != null){
            return controlRoedor.getFecha();
        }
        return null;
    }
    private static final long serialVersionUID = 1L;
}
