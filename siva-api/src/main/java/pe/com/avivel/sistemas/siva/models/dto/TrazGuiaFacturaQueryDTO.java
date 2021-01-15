package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.GuiaFactura;

@Data
public class TrazGuiaFacturaQueryDTO {

    private Integer id;

    private String numero;

    private int estado;


    public static TrazGuiaFacturaQueryDTO getInstance(GuiaFactura guiaFactura){

        if(guiaFactura == null) return null;

        TrazGuiaFacturaQueryDTO trazGuiaFacturaQueryDTO = new TrazGuiaFacturaQueryDTO();

        trazGuiaFacturaQueryDTO.setId(guiaFactura.getId());
        trazGuiaFacturaQueryDTO.setNumero(guiaFactura.getNumero());
        trazGuiaFacturaQueryDTO.setEstado(guiaFactura.getEstado());

        return trazGuiaFacturaQueryDTO;
    }
}
