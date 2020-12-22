package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FiltroConsumoDTO {

    private Date fechaDesde;
    private Date fechaHasta;
    private Integer prdGranjaId;
    private Integer zonasubzonacontrolId;

}
