package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGranja;

import java.io.Serializable;

@Data
public class PrdGranjaQueryDTO implements Serializable {

    private static final long serialVersionUID = 2464117895614705071L;
    private Integer id;
    private String codigo;
    private String nombre;

    public static PrdGranjaQueryDTO getInstance(PrdGranja granja) {
        if (granja == null) return null;

        PrdGranjaQueryDTO granjaQueryDTO = new PrdGranjaQueryDTO();
        granjaQueryDTO.setId(granja.getId());
        granjaQueryDTO.setCodigo(granja.getCodigo());
        granjaQueryDTO.setNombre(granja.getNombre());

        return granjaQueryDTO;
    }
}
