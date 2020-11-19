package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.trazabilidad.LoteSerie;

import java.util.Date;

@Data
public class TrazLoteSerieQueryDTO {


    private Integer id;
    private String numero;
    private Date vencimiento;
    private Integer cantidad;
    private Integer estado;

    public static TrazLoteSerieQueryDTO getInstance(LoteSerie loteSerie){
        if(loteSerie == null) return null;
        TrazLoteSerieQueryDTO trazLoteSerieQueryDTO = new TrazLoteSerieQueryDTO();
        trazLoteSerieQueryDTO.setNumero(loteSerie.getNumero());
        trazLoteSerieQueryDTO.setVencimiento(loteSerie.getVencimiento());
        trazLoteSerieQueryDTO.setId(loteSerie.getId());
        trazLoteSerieQueryDTO.setCantidad(loteSerie.getCantidad());
        trazLoteSerieQueryDTO.setEstado(loteSerie.getEstado());
        return trazLoteSerieQueryDTO;
    }

}
