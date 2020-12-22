package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class VacunacionDTO {

    private String codigo;
    private Date fecha;
    private String observacion;
    private Integer programacionVacunaId;
    private Integer loteId;
    private String loteCodigo;
    private Integer proveedorId;
    private Integer metodoVacunaId;
    private Integer empleadoId;
    private Integer loteSerieId;

}
