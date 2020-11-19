package pe.com.avivel.sistemas.siva.models.dto;


import lombok.Data;

@Data
public class SubFamiliaQueryDTO {

    private Integer id;
    private String codigo;
    private String descripcion;
    private FamiliaQueryDTO familiaQueryDTO;
}
