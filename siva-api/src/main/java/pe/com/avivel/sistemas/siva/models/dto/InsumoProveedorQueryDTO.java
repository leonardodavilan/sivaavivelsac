package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.InsumoProveedor;

import java.math.BigDecimal;

@Data
public class InsumoProveedorQueryDTO {

    private Long id;
    private InsumoPresentacionQueryDTO insumoPresentacionQueryDTO;
    private Integer proveedorId;
    private BigDecimal precio;
    private Integer monedaId;
    private String simbolo;

    public static InsumoProveedorQueryDTO getInstance(InsumoProveedor insumoProveedor) {
        if (insumoProveedor == null) return null;


        InsumoProveedorQueryDTO insumoProveedorQueryDTO = new InsumoProveedorQueryDTO();

        insumoProveedorQueryDTO.setId(insumoProveedor.getId());
        insumoProveedorQueryDTO.setInsumoPresentacionQueryDTO(InsumoPresentacionQueryDTO.getInstance(insumoProveedor.getInsumoPresentacion()));

        if(insumoProveedor.getProveedor() == null){
            insumoProveedorQueryDTO.setProveedorId(null);
        }else{
            insumoProveedorQueryDTO.setProveedorId(ProveedorQueryDTO.getInstance(insumoProveedor.getProveedor()).getId());
        }

        insumoProveedorQueryDTO.setPrecio(insumoProveedor.getPrecio());

        if(insumoProveedor.getMoneda() == null){
            insumoProveedorQueryDTO.setMonedaId(null);
            insumoProveedorQueryDTO.setSimbolo(null);
        }else{
            insumoProveedorQueryDTO.setMonedaId(MonedaQueryDTO.getInstance(insumoProveedor.getMoneda()).getId());
            insumoProveedorQueryDTO.setSimbolo(MonedaQueryDTO.getInstance(insumoProveedor.getMoneda()).getSimbolo());
        }

        return insumoProveedorQueryDTO;
    }


}
