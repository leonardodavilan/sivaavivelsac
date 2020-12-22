package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FiltroSolicitudDTO {

    private Integer codigoSolicitud;
    private Date fechaDesde;
    private Date fechaHasta;
    private Long estadoSolicitudId;
    private Integer empleadoId;
    private Integer proveedorId;

}
