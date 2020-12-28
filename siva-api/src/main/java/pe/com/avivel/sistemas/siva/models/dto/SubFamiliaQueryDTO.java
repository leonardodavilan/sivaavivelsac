package pe.com.avivel.sistemas.siva.models.dto;


import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.SubFamilia;

@Data
public class SubFamiliaQueryDTO {

    private Integer id;
    private String codigo;
    private String descripcion;
    private FamiliaQueryDTO familia;

    public static SubFamiliaQueryDTO getInstance(SubFamilia subFamilia) {
        if (subFamilia == null) return null;
        SubFamiliaQueryDTO subFamiliaQueryDTO = new SubFamiliaQueryDTO();

        subFamiliaQueryDTO.setId(subFamilia.getId());
        subFamiliaQueryDTO.setDescripcion(subFamilia.getDescripcion());
        subFamiliaQueryDTO.setCodigo(subFamilia.getCodigo());
        subFamiliaQueryDTO.setFamilia(FamiliaQueryDTO.getInstance(subFamilia.getFamilia()));
        return subFamiliaQueryDTO;
    }


}
