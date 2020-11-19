package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InsumoDetalladoDTO {
    private String insumo_descripcion;
    private Integer subFamilia_id;
    private String codigoRef;
    private String codigoSap;
    private Integer presentacionId;
    private Integer unidadMedidaId;
    private Integer proveedorId;
    private BigDecimal precio;
    private Integer monedaId;
}
