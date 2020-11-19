package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FiltroMovimientoDTO {

    private Integer prdGranjaId;
    private Date fechaDesde;
    private Date fechaHasta;
    private Integer tipoMovimientoId;
}
