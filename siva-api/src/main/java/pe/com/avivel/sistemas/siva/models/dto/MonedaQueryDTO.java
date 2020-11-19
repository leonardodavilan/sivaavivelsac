package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Moneda;

@Data
public class MonedaQueryDTO {

    private Integer id;

    private String codigo;

    private String simbolo;

    private String descripcion;

    private int estado;

    public static MonedaQueryDTO getInstance(Moneda moneda) {

        if (moneda == null) return null;

        MonedaQueryDTO monedaQueryDTO = new MonedaQueryDTO();

        monedaQueryDTO.setId(moneda.getId());
        monedaQueryDTO.setCodigo(moneda.getCodigo());
        monedaQueryDTO.setSimbolo(moneda.getSimbolo());
        monedaQueryDTO.setDescripcion(moneda.getDescripcion());
        monedaQueryDTO.setEstado(moneda.getEstado());

        return monedaQueryDTO;
    }
}
