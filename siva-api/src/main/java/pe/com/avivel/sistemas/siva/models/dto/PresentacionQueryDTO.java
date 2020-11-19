package pe.com.avivel.sistemas.siva.models.dto;


import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Presentacion;

@Data
public class PresentacionQueryDTO {

    private Integer id;

    private String nombre;

    private int estado;

    public static PresentacionQueryDTO getInstance(Presentacion presentacion) {
        if (presentacion == null) return null;


        PresentacionQueryDTO presentacionQueryDTO = new PresentacionQueryDTO();

        presentacionQueryDTO.setId(presentacion.getId());
        presentacionQueryDTO.setNombre(presentacion.getNombre());
        presentacionQueryDTO.setEstado(presentacion.getEstado());
        return presentacionQueryDTO;
    }
}
