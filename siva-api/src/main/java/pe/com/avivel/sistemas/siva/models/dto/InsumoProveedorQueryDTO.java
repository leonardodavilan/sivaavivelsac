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

    private ProveedorQueryDTO proveedorQueryDTO;
    private MonedaQueryDTO monedaQueryDTO;

    private String codigo;
    private String codigoSap;
    private String insumoDescripcion;
    private String insumoPresentacionNombre;
    private String unidadMedidaNombre;
    private String subFamiliaNombre;

    private String proveedorDescripcion;
    private String unidadMedidaSimbolo;



    public static InsumoProveedorQueryDTO getInstance(InsumoProveedor insumoProveedor) {
        if (insumoProveedor == null) return null;
        InsumoProveedorQueryDTO insumoProveedorQueryDTO = new InsumoProveedorQueryDTO();

        insumoProveedorQueryDTO.setId(insumoProveedor.getId());

        if(insumoProveedor.getProveedor() == null){
            insumoProveedorQueryDTO.setProveedorQueryDTO(null);
            insumoProveedorQueryDTO.setProveedorId(null);
            insumoProveedorQueryDTO.setProveedorDescripcion(null);
        }else{
            insumoProveedorQueryDTO.setProveedorQueryDTO(ProveedorQueryDTO.getInstance(insumoProveedor.getProveedor()));
            insumoProveedorQueryDTO.setProveedorId(ProveedorQueryDTO.getInstance(insumoProveedor.getProveedor()).getId());
            insumoProveedorQueryDTO.setProveedorDescripcion(ProveedorQueryDTO.getInstance(insumoProveedor.getProveedor()).getRazonSocial());
        }
        insumoProveedorQueryDTO.setPrecio(insumoProveedor.getPrecio());
        if(insumoProveedor.getMoneda() == null){
            insumoProveedorQueryDTO.setMonedaQueryDTO(null);
            insumoProveedorQueryDTO.setMonedaId(null);
            insumoProveedorQueryDTO.setSimbolo(null);
        }else{
            insumoProveedorQueryDTO.setMonedaQueryDTO(MonedaQueryDTO.getInstance(insumoProveedor.getMoneda()));
            insumoProveedorQueryDTO.setMonedaId(MonedaQueryDTO.getInstance(insumoProveedor.getMoneda()).getId());
            insumoProveedorQueryDTO.setSimbolo(MonedaQueryDTO.getInstance(insumoProveedor.getMoneda()).getSimbolo());
        }


        if(insumoProveedor.getInsumoPresentacion() == null){

            insumoProveedorQueryDTO.setCodigo(null);
            insumoProveedorQueryDTO.setCodigoSap(null);
            insumoProveedorQueryDTO.setInsumoDescripcion(null);
            insumoProveedorQueryDTO.setInsumoPresentacionNombre(null);
            insumoProveedorQueryDTO.setUnidadMedidaNombre(null);
            insumoProveedorQueryDTO.setSubFamiliaNombre(null);

        }else{
            insumoProveedorQueryDTO.setInsumoPresentacionQueryDTO(InsumoPresentacionQueryDTO.getInstance(insumoProveedor.getInsumoPresentacion()));
            insumoProveedorQueryDTO.setCodigo(InsumoPresentacionQueryDTO.getInstance(insumoProveedor.getInsumoPresentacion()).getCodigoRef());
            insumoProveedorQueryDTO.setCodigoSap(InsumoPresentacionQueryDTO.getInstance(insumoProveedor.getInsumoPresentacion()).getCodigoSap());
            insumoProveedorQueryDTO.setInsumoDescripcion(InsumoQueryDTO.getInstance(insumoProveedor.getInsumoPresentacion().getInsumo()).getDescripcion());
            if(insumoProveedor.getInsumoPresentacion().getPresentacion() == null)
            {
                insumoProveedorQueryDTO.setInsumoPresentacionNombre(null);
            }else{
                insumoProveedorQueryDTO.setInsumoPresentacionNombre(PresentacionQueryDTO.getInstance(insumoProveedor.getInsumoPresentacion().getPresentacion()).getNombre());
            }
            if (insumoProveedor.getInsumoPresentacion().getUnidadMedida() == null){
                insumoProveedorQueryDTO.setUnidadMedidaNombre(null);
                insumoProveedorQueryDTO.setUnidadMedidaSimbolo(null);
            }else{
                insumoProveedorQueryDTO.setUnidadMedidaNombre(UnidadMedidaQueryDTO.getInstance(insumoProveedor.getInsumoPresentacion().getUnidadMedida()).getNombre());
                insumoProveedorQueryDTO.setUnidadMedidaSimbolo(UnidadMedidaQueryDTO.getInstance(insumoProveedor.getInsumoPresentacion().getUnidadMedida()).getSimbolo());
            }
            if(insumoProveedor.getInsumoPresentacion().getInsumo().getSubFamilia() == null){
                insumoProveedorQueryDTO.setSubFamiliaNombre(null);

            }else{
                insumoProveedorQueryDTO.setSubFamiliaNombre(SubFamiliaQueryDTO.getInstance(insumoProveedor.getInsumoPresentacion().getInsumo().getSubFamilia()).getDescripcion());

            }
        }

        return insumoProveedorQueryDTO;
    }


}
