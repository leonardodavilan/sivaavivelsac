package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.produccion.LoteFechaProgramacion;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class LoteFechaProgramacionDTO {

    private String lote;
    private Integer población;
    private String edadProgramada;
    private String vacunaProgramada;
    private String presentacion;
    private BigDecimal precio;
    private String moneda;
    private Date fechaProgramada;
    private Integer cantidadCalculada;
    private BigDecimal total;

    public static LoteFechaProgramacionDTO getInstance(LoteFechaProgramacion loteFechaProgramacion){
        if(loteFechaProgramacion == null) return null;

        LoteFechaProgramacionDTO loteFechaProgramacionDTO = new LoteFechaProgramacionDTO();
        loteFechaProgramacionDTO.setLote(loteFechaProgramacion.getLoteCodigo());
        loteFechaProgramacionDTO.setPoblación(loteFechaProgramacionDTO.getPoblación());
        loteFechaProgramacionDTO.setEdadProgramada( loteFechaProgramacion.getProgramacionVacuna().getTiempoProgramacion().getNombre() + loteFechaProgramacion.getEdad().toString());
        loteFechaProgramacionDTO.setVacunaProgramada(loteFechaProgramacion.getInsumoDescripcion());

        if(loteFechaProgramacion.getPresentacion() == null){
            loteFechaProgramacionDTO.setPresentacion(null);
        }else{
            loteFechaProgramacionDTO.setPresentacion(loteFechaProgramacion.getPresentacion().getNombre());
        }

        loteFechaProgramacionDTO.setPrecio(loteFechaProgramacion.getPrecio());

        if(loteFechaProgramacion.getInsumoProveedor().getMoneda() == null){
            loteFechaProgramacionDTO.setMoneda(null);
        }else{
            loteFechaProgramacionDTO.setMoneda(loteFechaProgramacion.getInsumoProveedor().getMoneda().getCodigo());
        }

        loteFechaProgramacionDTO.setFechaProgramada(loteFechaProgramacion.getFechaProgramada());
        loteFechaProgramacionDTO.setCantidadCalculada(loteFechaProgramacion.getCantidadCalculada());
        loteFechaProgramacionDTO.setTotal(loteFechaProgramacion.getTotal());
        return loteFechaProgramacionDTO;
    }
}
