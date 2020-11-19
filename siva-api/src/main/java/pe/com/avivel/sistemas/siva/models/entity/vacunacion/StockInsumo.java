package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGranja;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "vac_stock_insumos")
public class StockInsumo implements Serializable {

    @Id
    @Column(name="stock_insumo_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="stock_insumo_cantidad")
    private int cantidad;

    @ManyToOne(targetEntity = Insumo.class)
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    @ManyToOne(targetEntity = PrdGranja.class)
    @JoinColumn(name = "granja_id")
    private PrdGranja prdGranja;


    private static final long serialVersionUID = 1L;
}
