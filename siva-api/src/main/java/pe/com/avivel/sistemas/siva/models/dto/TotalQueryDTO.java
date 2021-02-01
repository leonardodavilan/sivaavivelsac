package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;

@Data
public class TotalQueryDTO {

    private Double total;
    private String moneda;

    public TotalQueryDTO(){

    }
    public TotalQueryDTO(Double total, String moneda){
        this.total = total;
        this.moneda = moneda;
    }
}
