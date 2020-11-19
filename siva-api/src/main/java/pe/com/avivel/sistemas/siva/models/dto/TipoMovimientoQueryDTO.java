package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.TipoMovimiento;

@Data
public class TipoMovimientoQueryDTO {

    private Integer id;
    private String nombre;
    private int estado;

    public static TipoMovimientoQueryDTO getInstance(TipoMovimiento tipoMovimiento) {
        if (tipoMovimiento == null) return null;


        TipoMovimientoQueryDTO tipoMovimientoQueryDTO = new TipoMovimientoQueryDTO();

        tipoMovimientoQueryDTO.setId(tipoMovimiento.getId());
        tipoMovimientoQueryDTO.setNombre(tipoMovimiento.getNombre());
        tipoMovimientoQueryDTO.setEstado(tipoMovimiento.getEstado());

        return tipoMovimientoQueryDTO;
    }
}
