package pe.com.avivel.sistemas.siva.models.dto;


import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Insumo;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.NumeroProgramacion;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.ProgramacionVacuna;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.TiempoProgramacion;

@Data
public class ProgramacionQueryDTO {

    private Integer id;
    private Integer numeroProgCodigo;
    private Integer numProgId;
    private String tiempoProgNombre;
    private Integer tiempoProgId;
    private Integer progVacunaEdad;
    private String descripcion;
    private Integer insumoId;
    private String descripcionVacunacion;
    private String etapa;
    private Boolean estado;

    public static ProgramacionQueryDTO getInstance(ProgramacionVacuna programacionVacuna) {
        if (programacionVacuna == null) return null;

        NumeroProgramacion numeroProgramacion = new NumeroProgramacion();
        numeroProgramacion.setCodigo(programacionVacuna.getNumeroProgramacion().getCodigo());
        numeroProgramacion.setId(programacionVacuna.getNumeroProgramacion().getId());

        TiempoProgramacion tiempoProgramacion = new TiempoProgramacion();
        tiempoProgramacion.setNombre(programacionVacuna.getTiempoProgramacion().getNombre());
        tiempoProgramacion.setId(programacionVacuna.getTiempoProgramacion().getId());

        Insumo insumo = new Insumo();
        insumo.setDescripcion(programacionVacuna.getInsumo().getDescripcion());
        insumo.setId(programacionVacuna.getInsumo().getId());

        ProgramacionQueryDTO programacionQueryDTO = new ProgramacionQueryDTO();
        programacionQueryDTO.setId(programacionVacuna.getId());

        programacionQueryDTO.setDescripcion(insumo.getDescripcion());

        programacionQueryDTO.setNumProgId(numeroProgramacion.getId());
        programacionQueryDTO.setNumeroProgCodigo(numeroProgramacion.getCodigo());

        programacionQueryDTO.setTiempoProgId(tiempoProgramacion.getId());
        programacionQueryDTO.setInsumoId(insumo.getId());

        programacionQueryDTO.setDescripcionVacunacion(programacionVacuna.getDescripcionVacunacion());
        programacionQueryDTO.setTiempoProgNombre(tiempoProgramacion.getNombre());
        programacionQueryDTO.setProgVacunaEdad(programacionVacuna.getEdadVacunacion());
        programacionQueryDTO.setEtapa(programacionVacuna.getPrdEtapa().getNombre());
        programacionQueryDTO.setEstado(programacionVacuna.getEstado());

        return programacionQueryDTO;
    }
}
