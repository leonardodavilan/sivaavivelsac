package pe.com.avivel.sistemas.siva.models.dto;


import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.*;

import java.util.Date;

@Data
public class VacunacionQueryDTO {

    private Integer id;
    private String programacion;
    private String lote;
    private String vacuna;
    private String metodoVacuna;
    private String tiempo;
    private Integer edad;
    private Date fechaVacunacion;
    private ProveedorQueryDTO proveedor;
    private TrazLoteSerieQueryDTO loteSerie;
    private Date fechaVencimiento;
    private String observacion;

    public static VacunacionQueryDTO getInstance(Vacunacion vacunacion) {
        if (vacunacion == null) return null;


        VacunacionQueryDTO vacunacionQueryDTO = new VacunacionQueryDTO();

        vacunacionQueryDTO.setId(vacunacion.getId());
        vacunacionQueryDTO.setProgramacion(vacunacion.getProgramacionVacuna().getNumeroProgramacion().getNombre());
        vacunacionQueryDTO.setLote(vacunacion.getLote().getCodigo());
        vacunacionQueryDTO.setVacuna(vacunacion.getProgramacionVacuna().getInsumo().getDescripcion());
        vacunacionQueryDTO.setTiempo(vacunacion.getProgramacionVacuna().getTiempoProgramacion().getNombre());

        if(vacunacion.getMetodoVacuna() == null){
            vacunacionQueryDTO.setMetodoVacuna("");
        }else{
            vacunacionQueryDTO.setMetodoVacuna(vacunacion.getMetodoVacuna().getNombre());
        }

        vacunacionQueryDTO.setLoteSerie(TrazLoteSerieQueryDTO.getInstance(vacunacion.getLoteSerie()));
        vacunacionQueryDTO.setEdad(vacunacion.getProgramacionVacuna().getEdadVacunacion());
        vacunacionQueryDTO.setFechaVacunacion(vacunacion.getFecha());
        vacunacionQueryDTO.setProveedor(ProveedorQueryDTO.getInstance(vacunacion.getProveedor()));
        vacunacionQueryDTO.setObservacion(vacunacion.getObservacion());

        return vacunacionQueryDTO;
    }
}
