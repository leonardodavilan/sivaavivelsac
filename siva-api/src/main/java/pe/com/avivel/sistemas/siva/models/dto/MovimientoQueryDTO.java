package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Movimiento;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MovimientoQueryDTO {

    private Integer id;
    private TipoMovimientoQueryDTO tipoMovimiento;
    private PrdGranjaQueryDTO granja;
    private Date fechaMovimiento;
    private InsumoPresentacionQueryDTO insumoPresentacionQueryDTO;
    private String insumoDescripcion;
    private ProveedorQueryDTO proveedorQueryDTO;
    private InsumoProveedorQueryDTO insumoProveedorQueryDTO;
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
        movimientoQueryDTO.setTipoMovimiento(TipoMovimientoQueryDTO.getInstance(movimiento.getTipoMovimiento()));
        movimientoQueryDTO.setFechaMovimiento(movimiento.getFecha());
        movimientoQueryDTO.setInsumoPresentacionQueryDTO(InsumoPresentacionQueryDTO.getInstance(movimiento.getInsumoProveedor().getInsumoPresentacion()));

        movimientoQueryDTO.setInsumoDescripcion(movimiento.getInsumoProveedor().getInsumoPresentacion().getInsumo().getDescripcion());

        movimientoQueryDTO.setProveedorQueryDTO(ProveedorQueryDTO.getInstance(movimiento.getInsumoProveedor().getProveedor()));

        movimientoQueryDTO.setPrecio(movimiento.getInsumoProveedor().getPrecio());
        movimientoQueryDTO.setInsumoProveedorQueryDTO(InsumoProveedorQueryDTO.getInstance(movimiento.getInsumoProveedor()));

        movimientoQueryDTO.setGranja(PrdGranjaQueryDTO.getInstance(movimiento.getPrdGranja()));
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
