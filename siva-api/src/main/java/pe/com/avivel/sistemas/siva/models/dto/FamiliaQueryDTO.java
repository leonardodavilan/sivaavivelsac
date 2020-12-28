package pe.com.avivel.sistemas.siva.models.dto;


import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Familia;

@Data
public class FamiliaQueryDTO {


    private Integer id;

    private String codigo;

    private String descripcion;

    public static FamiliaQueryDTO getInstance(Familia familia) {
        if (familia == null) return null;


        FamiliaQueryDTO familiaQueryDTO = new FamiliaQueryDTO();

        familiaQueryDTO.setId(familia.getId());
        familiaQueryDTO.setDescripcion(familia.getDescripcion());
        familiaQueryDTO.setCodigo(familia.getCodigo());
        return familiaQueryDTO;
    }
}
