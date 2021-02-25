package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import pe.com.avivel.sistemas.siva.models.entity.auditoria.Auditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Audited
@Entity
@Data
@Table(name = "traz_lotesseries")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class LoteSerie extends Auditable<String> {

    @Id
    @Column(name = "loteserie_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "loteserie_numero")
    private String numero;

    @Column(name = "loteserie_fechavencimiento")
    @Temporal(value = TemporalType.DATE)
    private Date vencimiento;

    @Column(name = "loteserie_cantidad")
    private Integer cantidad;

    @Column(name = "loteserie_estado")
    private Integer estado;

    @JsonIgnoreProperties(value={"loteSerie", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "loteserie_id")
    private List<Movimiento> movimientos;

    private static final long serialVersionUID = -5313905989469067926L;

    public LoteSerie setId(Integer id){
        this.id = id;
        return this;
    }

}
