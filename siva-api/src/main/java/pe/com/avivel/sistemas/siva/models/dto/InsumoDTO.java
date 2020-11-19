package pe.com.avivel.sistemas.siva.models.dto;


import lombok.Data;

@Data
public class InsumoDTO {

    private String insumo_descripcion;
    private Integer subFamilia_id;


    public String getInsumo_descripcion() {
        return insumo_descripcion;
    }

    public void setInsumo_descripcion(String insumo_descripcion) {
        this.insumo_descripcion = insumo_descripcion;
    }

    public Integer getSubFamilia_id() {
        return subFamilia_id;
    }

    public void setSubFamilia_id(Integer subFamilia_id) {
        this.subFamilia_id = subFamilia_id;
    }
}
