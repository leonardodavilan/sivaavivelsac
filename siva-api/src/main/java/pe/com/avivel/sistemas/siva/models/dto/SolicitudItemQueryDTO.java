package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.SolicitudItem;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SolicitudItemQueryDTO {

    private Integer codigo;
    private String centroCosto;
    private String usuarioPedido;
    private Date fecha;
    private String insumo;
    private String presentacion;
    private BigDecimal precio;
    private String moneda;
    private String aplicacion;
    private BigDecimal cantidad;
    private BigDecimal total;

    public static SolicitudItemQueryDTO getInstance(SolicitudItem solicitudItem){

        if(solicitudItem == null ){
            return null;
        }

        SolicitudItemQueryDTO solicitudItemDTO = new SolicitudItemQueryDTO();

        solicitudItemDTO.setCentroCosto(solicitudItem.getSolicitud().getCcosto());
        solicitudItemDTO.setUsuarioPedido(solicitudItem.getSolicitud().getUsuarioPedido());
        solicitudItemDTO.setCodigo(solicitudItem.getSolicitud().getCodigo());
        solicitudItemDTO.setFecha(solicitudItem.getSolicitud().getFecha());
        solicitudItemDTO.setPrecio(solicitudItem.getPrecioPedido());

        if (solicitudItem.getInsumoProveedor() == null){
            solicitudItemDTO.setInsumo(null);
            solicitudItemDTO.setPresentacion(null);
            solicitudItemDTO.setMoneda(null);
        }else{
            if(solicitudItem.getInsumoProveedor().getInsumoPresentacion() == null){
                solicitudItemDTO.setInsumo(null);
            }else{
                solicitudItemDTO.setInsumo(solicitudItem.getInsumoProveedor().getInsumoPresentacion().getInsumo().getDescripcion());
                if(solicitudItem.getInsumoProveedor().getInsumoPresentacion().getPresentacion() == null){
                    solicitudItemDTO.setPresentacion(null);
                }else{solicitudItemDTO.setPresentacion(solicitudItem.getInsumoProveedor().getInsumoPresentacion().getPresentacion().getNombre());}

            }
            if(solicitudItem.getInsumoProveedor().getMoneda() == null){
                solicitudItemDTO.setMoneda(null);
            }else{solicitudItemDTO.setMoneda(solicitudItem.getInsumoProveedor().getMoneda().getCodigo());}

        }
        solicitudItemDTO.setAplicacion(solicitudItem.getAplicacion());
        solicitudItemDTO.setCantidad(solicitudItem.getCantidad());
        solicitudItemDTO.setTotal(solicitudItem.getImporte());

        return solicitudItemDTO;

    }

}
