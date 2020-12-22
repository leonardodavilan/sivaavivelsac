package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;

@Data
public class SaldosDTO {
    private Integer granjaId;
    private String granjaNombre;
    private Double saldo;

    public SaldosDTO(Integer granjaId, String granjaNombre, Double saldo) {
        this.granjaId = granjaId;
        this.granjaNombre = granjaNombre;
        this.saldo = saldo;
    }
}
