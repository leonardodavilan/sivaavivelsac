package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdEtapa;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@Table(name = "vac_programaciones_vacunas")
public class ProgramacionVacuna implements Serializable {

    @Id
    @Column(name="programacion_vacuna_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = NumeroProgramacion.class)
    @JoinColumn(name = "numero_programacion_id")
    private NumeroProgramacion numeroProgramacion;

    @Column(name="programacion_vacuna_edad")
    private Integer edadVacunacion;

    @ManyToOne(targetEntity = TiempoProgramacion.class)
    @JoinColumn(name = "tiempo_programacion_id")
    private TiempoProgramacion tiempoProgramacion;

    @ManyToOne(targetEntity = Insumo.class)
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    @Column(name="programacion_vacuna_descripcion")
    private String descripcionVacunacion;

    @ManyToOne(targetEntity = PrdEtapa.class)
    @JoinColumn(name = "etapa_id")
    private PrdEtapa prdEtapa;

    @Column(name="programacion_vacuna_estado")
    private int estado;

    private static final long serialVersionUID = 1L;

    public ProgramacionVacuna setId(Integer id){
        this.id = id;
        return this;
    }
}
