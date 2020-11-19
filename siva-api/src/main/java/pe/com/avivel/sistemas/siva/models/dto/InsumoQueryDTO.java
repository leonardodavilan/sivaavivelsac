package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Insumo;

@Data
public class InsumoQueryDTO {

    private Integer id;
    private String insumo_descripcion;
    private Integer subFamilia_id;

    public static InsumoQueryDTO getInstance(Insumo insumo) {
        if (insumo == null) return null;


        InsumoQueryDTO insumoQueryDTO = new InsumoQueryDTO();

        insumoQueryDTO.setId(insumo.getId());
        insumoQueryDTO.setInsumo_descripcion(insumo.getDescripcion());
        insumoQueryDTO.setSubFamilia_id(insumo.getSubFamilia().getId());
        return insumoQueryDTO;
    }
}
