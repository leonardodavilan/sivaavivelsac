package pe.com.avivel.sistemas.siva.models.entity.vacunacion;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.dto.TotalQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGranja;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "vac_stock_insumos")
public class StockInsumo implements Serializable {

    @Id
    @Column(name="stock_insumo_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="stock_insumo_cantidad")
    private BigDecimal cantidad;

    @ManyToOne(targetEntity = LoteSerie.class)
    @JoinColumn(name = "loteserie_id")
    private LoteSerie loteSerie;

    @ManyToOne(targetEntity = PrdGranja.class)
    @JoinColumn(name = "granja_id")
    private PrdGranja prdGranja;

    @ManyToOne(targetEntity = InsumoProveedor.class)
    @JoinColumn(name = "insumo_proveedor_id")
    private InsumoProveedor insumoProveedor;

    private static final long serialVersionUID = 1L;

    public TotalQueryDTO totalQueryDTO(){
        TotalQueryDTO totalQueryDTO = new TotalQueryDTO();
        totalQueryDTO.setTotal(insumoProveedor.getPrecio().doubleValue() * cantidad.doubleValue());
        totalQueryDTO.setMoneda(insumoProveedor.getMoneda().getSimbolo());
        return totalQueryDTO;
    }
}
