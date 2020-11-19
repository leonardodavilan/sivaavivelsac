package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.InsumoPresentacion;

@Data
public class InsumoPresentacionQueryDTO {

    private Integer id;
    private String codigoRef;
    private String codigoSap;
    private InsumoQueryDTO insumoQueryDTO;
    private PresentacionQueryDTO presentacionQueryDTO;
    private UnidadMedidaQueryDTO unidadMedidaQueryDTO;

    public static InsumoPresentacionQueryDTO getInstance(InsumoPresentacion insumoPresentacion) {
        if (insumoPresentacion == null) return null;


        InsumoPresentacionQueryDTO insumoPresentacionQueryDTO = new InsumoPresentacionQueryDTO();

        insumoPresentacionQueryDTO.setId(insumoPresentacion.getId());
        insumoPresentacionQueryDTO.setCodigoRef(insumoPresentacion.getCodigoRef());
        insumoPresentacionQueryDTO.setCodigoSap(insumoPresentacion.getCodigoSap());
        insumoPresentacionQueryDTO.setInsumoQueryDTO(InsumoQueryDTO.getInstance(insumoPresentacion.getInsumo()));
        insumoPresentacionQueryDTO.setPresentacionQueryDTO(PresentacionQueryDTO.getInstance(insumoPresentacion.getPresentacion()));
        insumoPresentacionQueryDTO.setUnidadMedidaQueryDTO(UnidadMedidaQueryDTO.getInstance(insumoPresentacion.getUnidadMedida()));

        return insumoPresentacionQueryDTO;
    }


}
