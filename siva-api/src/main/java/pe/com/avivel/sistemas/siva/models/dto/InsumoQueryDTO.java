package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Insumo;

@Data
public class InsumoQueryDTO {

    private Integer id;
    private String descripcion;
    private SubFamiliaQueryDTO subFamiliaQueryDTO;
    private Integer subFamiliaId;

    public static InsumoQueryDTO getInstance(Insumo insumo) {
        if (insumo == null) return null;


        InsumoQueryDTO insumoQueryDTO = new InsumoQueryDTO();

        insumoQueryDTO.setId(insumo.getId());
        insumoQueryDTO.setDescripcion(insumo.getDescripcion());
        insumoQueryDTO.setSubFamiliaId(insumo.getSubFamilia().getId());

        if(insumo.getSubFamilia() == null){
            insumoQueryDTO.setSubFamiliaQueryDTO(null);
        }else{
            insumoQueryDTO.setSubFamiliaQueryDTO(SubFamiliaQueryDTO.getInstance(insumo.getSubFamilia()));
        }

        return insumoQueryDTO;
    }
}
