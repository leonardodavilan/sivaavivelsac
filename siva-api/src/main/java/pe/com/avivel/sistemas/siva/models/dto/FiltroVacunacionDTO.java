package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FiltroVacunacionDTO {

    private String prdEtapa;
    private Date fechaDesde;
    private Date fechaHasta;
    private Integer prdLoteId;
}
