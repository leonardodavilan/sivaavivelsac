package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.UnidadMedida;

@Data
public class UnidadMedidaQueryDTO {

    private Integer id;

    private String simbolo;

    private String nombre;

    private String codigo;

    private int estado;

    public static UnidadMedidaQueryDTO getInstance(UnidadMedida unidadMedida) {
        if (unidadMedida == null) return null;


        UnidadMedidaQueryDTO unidadMedidaQueryDTO = new UnidadMedidaQueryDTO();

        unidadMedidaQueryDTO.setId(unidadMedida.getId());
        unidadMedidaQueryDTO.setSimbolo(unidadMedida.getSimbolo());
        unidadMedidaQueryDTO.setNombre(unidadMedida.getNombre());
        unidadMedidaQueryDTO.setEstado(unidadMedida.getEstado());
        return unidadMedidaQueryDTO;
    }
}
