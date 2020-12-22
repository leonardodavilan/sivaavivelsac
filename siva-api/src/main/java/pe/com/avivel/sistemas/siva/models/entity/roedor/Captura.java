package pe.com.avivel.sistemas.siva.models.entity.roedor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "roe_capturas")
public class Captura implements Serializable {

    @Id
    @Column(name="captura_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="captura_numeros_capturas")
    private int numCapturas;

    @ManyToOne(targetEntity = AreaCaptura.class)
    @JoinColumn(name = "area_captura_id")
    private AreaCaptura areaCaptura;

    @ManyToOne(targetEntity = Edad.class)
    @JoinColumn(name = "edad_id")
    private Edad edad;

    @ManyToOne(targetEntity = Sexo.class)
    @JoinColumn(name = "sexo_id")
    private Sexo sexo;

    @Column(name="captura_estado")
    private int estado;

    @JsonIgnoreProperties(value={"capturas", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "consumo_id")
    private Consumo consumo;

    private static final long serialVersionUID = 1L;

}
