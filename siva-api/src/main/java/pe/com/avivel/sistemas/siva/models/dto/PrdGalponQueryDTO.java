package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdGalpon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PrdGalponQueryDTO implements Serializable {
    private static final long serialVersionUID = 1489399993753937234L;

    private Integer id;

    private String codigo;

    private Integer capacidad;

    private Integer capacidadUtilizada;

    private String descripcion;

    private PrdGranjaQueryDTO granja;

    public static PrdGalponQueryDTO getInstance(PrdGalpon galpon) {
        if (galpon == null) galpon = new PrdGalpon();

        PrdGalponQueryDTO galponQueryDTO = new PrdGalponQueryDTO();
        galponQueryDTO.setId(galpon.getId());
        galponQueryDTO.setCodigo(galpon.getCodigo());
        galponQueryDTO.setCapacidad(galpon.getCapacidad());
        galponQueryDTO.setCapacidadUtilizada(galpon.getCapacidadUtilizada());
        galponQueryDTO.setDescripcion(galpon.getDescripcion());
        galponQueryDTO.setGranja(PrdGranjaQueryDTO.getInstance(galpon.getGranja()));
        return galponQueryDTO;
    }

    public static List<PrdGalponQueryDTO> getInstance(List<PrdGalpon> galpones) {
        if (galpones == null) return null;

        List<PrdGalponQueryDTO> galponesQueryDTO = new ArrayList<>();
        galpones.forEach(g -> {
            galponesQueryDTO.add(PrdGalponQueryDTO.getInstance(g));
        });

        return galponesQueryDTO;
    }
}
