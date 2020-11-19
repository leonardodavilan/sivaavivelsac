package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;

@Data
public class FiltroProgramacionDTO {
    private String prdEtapa;
    private Integer numProgId;
    private Integer tiempoProgId;
    private Integer edad;
    private Integer insumoId;
}
