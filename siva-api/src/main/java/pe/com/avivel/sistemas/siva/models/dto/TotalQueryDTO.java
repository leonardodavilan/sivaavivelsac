package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;

@Data
public class TotalQueryDTO {

    private Double total;
    private String moneda;
    private String monedaCodigo;

    public TotalQueryDTO(){

    }
    public TotalQueryDTO(Double total, String moneda, String monedaCodigo){
        this.total = total;
        this.moneda = moneda;
        this.monedaCodigo = monedaCodigo;
    }
}
