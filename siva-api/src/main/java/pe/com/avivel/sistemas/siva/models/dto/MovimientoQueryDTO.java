package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Movimiento;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MovimientoQueryDTO {

    private Integer id;
    private TipoMovimientoQueryDTO tipoMovimiento;
    private String tipoMovimientoNombre;

    private PrdGranjaQueryDTO granja;
    private String granjaNombre;

    private Date fechaMovimiento;
    private InsumoPresentacionQueryDTO insumoPresentacionQueryDTO;
    private String insumoCodigo;
    private String insumoDescripcionP;

    private ProveedorQueryDTO proveedorQueryDTO;
    private String proveedorNombre;

    private InsumoProveedorQueryDTO insumoProveedorQueryDTO;
    private String monedaCodigo;

    private BigDecimal precio;
    private BigDecimal cantidad;
    private TrazLoteSerieQueryDTO trazLoteSerieQueryDTO;
    private TrazGuiaFacturaQueryDTO trazGuiaFacturaQueryDTO;
    private Date fechaVencimiento;

    private TotalQueryDTO totalQueryDTO;


    public static MovimientoQueryDTO getInstance(Movimiento movimiento) {

        if (movimiento == null) return null;

        MovimientoQueryDTO movimientoQueryDTO = new MovimientoQueryDTO();

        movimientoQueryDTO.setId(movimiento.getId());
        if(movimiento.getTipoMovimiento() == null){
            movimientoQueryDTO.setTipoMovimiento(null);
            movimientoQueryDTO.setTipoMovimientoNombre(null);
        }else{
            movimientoQueryDTO.setTipoMovimiento(TipoMovimientoQueryDTO.getInstance(movimiento.getTipoMovimiento()));
            movimientoQueryDTO.setTipoMovimientoNombre(movimiento.getTipoMovimiento().getNombre());
        }

        movimientoQueryDTO.setFechaMovimiento(movimiento.getFecha());

        if(movimiento.getInsumoProveedor().getInsumoPresentacion() == null){
            movimientoQueryDTO.setInsumoPresentacionQueryDTO(null);
            movimientoQueryDTO.setInsumoCodigo(null);
        }else{
            movimientoQueryDTO.setInsumoCodigo(movimiento.getInsumoProveedor().getInsumoPresentacion().getCodigoSap());
            movimientoQueryDTO.setInsumoPresentacionQueryDTO(InsumoPresentacionQueryDTO.getInstance(movimiento.getInsumoProveedor().getInsumoPresentacion()));
            if(movimiento.getInsumoProveedor().getInsumoPresentacion().getPresentacion() == null){
                movimientoQueryDTO.setInsumoDescripcionP(movimiento.getInsumoProveedor().getInsumoPresentacion().getInsumo().getDescripcion());
            }else{
                movimientoQueryDTO.setInsumoDescripcionP(movimiento.getInsumoProveedor().getInsumoPresentacion().getInsumo().getDescripcion() +" "+ movimiento.getInsumoProveedor().getInsumoPresentacion().getPresentacion().getNombre());
            }
        }



        movimientoQueryDTO.setProveedorQueryDTO(ProveedorQueryDTO.getInstance(movimiento.getInsumoProveedor().getProveedor()));
        if(movimiento.getInsumoProveedor().getProveedor() == null){
            movimientoQueryDTO.setProveedorNombre(null);

        }else{
            movimientoQueryDTO.setProveedorNombre(movimiento.getInsumoProveedor().getProveedor().getRazonSocial());
        }

        movimientoQueryDTO.setPrecio(movimiento.getInsumoProveedor().getPrecio());
        movimientoQueryDTO.setInsumoProveedorQueryDTO(InsumoProveedorQueryDTO.getInstance(movimiento.getInsumoProveedor()));
        if(movimiento.getInsumoProveedor().getMoneda() == null){
            movimientoQueryDTO.setMonedaCodigo(null);
        }else{
            movimientoQueryDTO.setMonedaCodigo(movimiento.getInsumoProveedor().getMoneda().getCodigo());
        }

        if(movimiento.getPrdGranja() == null){
            movimientoQueryDTO.setGranjaNombre(null);
            movimientoQueryDTO.setGranja(null);

        }else{
            movimientoQueryDTO.setGranja(PrdGranjaQueryDTO.getInstance(movimiento.getPrdGranja()));
            movimientoQueryDTO.setGranjaNombre(movimiento.getPrdGranja().getNombre());
        }


        movimientoQueryDTO.setCantidad(movimiento.getCantidad());
        movimientoQueryDTO.setTrazLoteSerieQueryDTO(TrazLoteSerieQueryDTO.getInstance(movimiento.getLoteSerie()));
        movimientoQueryDTO.setTrazGuiaFacturaQueryDTO(TrazGuiaFacturaQueryDTO.getInstance(movimiento.getGuiaFactura()));

        if(movimiento.getLoteSerie() == null){
            movimientoQueryDTO.setFechaVencimiento(null);
        }else{
            movimientoQueryDTO.setFechaVencimiento(movimiento.getLoteSerie().getVencimiento());
        }

        movimientoQueryDTO.setTotalQueryDTO(movimiento.totalQueryDTO());


        return movimientoQueryDTO;
    }
}
