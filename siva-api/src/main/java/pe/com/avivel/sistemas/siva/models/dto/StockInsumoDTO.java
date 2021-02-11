package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.ProgramacionVacuna;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.StockInsumo;

import java.math.BigDecimal;

@Data
public class StockInsumoDTO {

    private Integer id;
    private String granja;
    private String insumo;
    private String presentacion;
    private BigDecimal saldo;
    private BigDecimal precioInsumo;
    private TotalQueryDTO totalQueryDTO;

    public static StockInsumoDTO getInstance(StockInsumo stockInsumo) {
        if (stockInsumo == null) return null;
        StockInsumoDTO stockInsumoDTO = new StockInsumoDTO();
        stockInsumoDTO.setId(stockInsumo.getId());
        stockInsumoDTO.setGranja(stockInsumo.getPrdGranja().getNombre());
        if(stockInsumo.getInsumoProveedor().getInsumoPresentacion().getPresentacion() == null) {
            stockInsumoDTO.setPresentacion(null);
        }else{
            stockInsumoDTO.setPresentacion(stockInsumo.getInsumoProveedor().getInsumoPresentacion().getPresentacion().getNombre());
        }
        stockInsumoDTO.setSaldo(stockInsumo.getCantidad());

        if(stockInsumo.getInsumoProveedor() == null) {
            stockInsumoDTO.setPrecioInsumo(null);
        }else{
            stockInsumoDTO.setPrecioInsumo(stockInsumo.getInsumoProveedor().getPrecio());
        }
        stockInsumoDTO.setTotalQueryDTO(stockInsumo.totalQueryDTO());

        return stockInsumoDTO;
    }
}
