package pe.com.avivel.sistemas.siva.models.dto;


import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Presentacion;

import java.math.BigDecimal;

@Data
public class VacunaCalculadaQueryDTO {


    private Presentacion presentacion;

    private BigDecimal cantidad;

    private int diferencia;


    public VacunaCalculadaQueryDTO(Presentacion presentacion, BigDecimal cantidad, int diferencia) {
        this.presentacion = presentacion;
        this.cantidad = cantidad;
        this.diferencia = diferencia;
    }

    public VacunaCalculadaQueryDTO() {

    }
}
