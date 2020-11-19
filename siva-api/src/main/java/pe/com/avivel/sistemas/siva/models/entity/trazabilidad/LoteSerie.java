package pe.com.avivel.sistemas.siva.models.entity.trazabilidad;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "traz_lotesseries")
public class LoteSerie implements Serializable {

    @Id
    @Column(name = "loteserie_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    private static final long serialVersionUID = 1L;

    public LoteSerie setId(Integer id){
        this.id = id;
        return this;
    }
}
