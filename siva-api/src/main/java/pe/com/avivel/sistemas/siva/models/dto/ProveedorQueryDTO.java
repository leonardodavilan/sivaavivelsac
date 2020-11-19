package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Proveedor;


@Data
public class ProveedorQueryDTO {

    private Integer id;

    private String razonSocial;

    private String ruc;

    private int estado;

    public static ProveedorQueryDTO getInstance(Proveedor proveedor) {
        if (proveedor == null) return null;


        ProveedorQueryDTO proveedorQueryDTO = new ProveedorQueryDTO();

        proveedorQueryDTO.setId(proveedor.getId());
        proveedorQueryDTO.setRazonSocial(proveedor.getRazonSocial());
        proveedorQueryDTO.setRuc(proveedor.getRuc());
        proveedorQueryDTO.setEstado(proveedor.getEstado());

        return proveedorQueryDTO;
    }
}
