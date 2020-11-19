package pe.com.avivel.sistemas.siva.models.dto;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.entity.produccion.PrdLote;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Data
public class PrdLoteQueryDTO {


    private Integer id;

    private String codigo;

    private Integer numeroInicialAves;

    private Date fechaIngreso;

    private Date fechaCierre;

    private PrdGalponQueryDTO galpon;

    private String loteEtapa;

    private String lineaGeneticaNombre;

    private String lineaGeneticaNombreCorto;

    private PrdLoteQueryDTO loteParent;

    private int activo;

    private PrdLoteEdadQueryDTO loteEdad;

    private Integer poblacionFinal;

    private boolean cerrado;

    private Date fechaInicioProduccion;

    public static PrdLoteQueryDTO getInstance(PrdLote lote) {
        if (lote == null) return null;

        PrdLoteQueryDTO loteQueryDTO = new PrdLoteQueryDTO();
        loteQueryDTO.setId(lote.getId());
        loteQueryDTO.setCodigo(lote.getCodigo());
        loteQueryDTO.setNumeroInicialAves(lote.getNumeroInicialAves());
        loteQueryDTO.setFechaIngreso(lote.getFechaIngreso());
        loteQueryDTO.setGalpon(PrdGalponQueryDTO.getInstance(lote.getGalpon()));
        loteQueryDTO.setLoteParent(PrdLoteQueryDTO.getInstance(lote.getPrdLoteParent()));
        loteQueryDTO.setLoteEtapa(lote.getLoteEtapa());
        loteQueryDTO.setLineaGeneticaNombre(lote.getLineaGeneticaNombre());
        loteQueryDTO.setLineaGeneticaNombreCorto(lote.getLineaGeneticaNombreCorto());
        loteQueryDTO.setActivo(lote.getLoteActivo());
        loteQueryDTO.setFechaInicioProduccion(lote.getFechaInicioProduccion());
        return loteQueryDTO;
    }

    public PrdLoteEdadQueryDTO getLoteEdad() {
        if (getFechaIngreso() == null)
            return null;
        else {
            PrdLoteEdadQueryDTO loteEdadQueryDTO = new PrdLoteEdadQueryDTO();

            Date fechaIngreso;
            if (getLoteEtapa() == "POSTURA" && getLoteParent() != null) {
                fechaIngreso = getLoteParent().getFechaIngreso();
            } else {
                fechaIngreso = getFechaIngreso();
            }

            LocalDate fechaLote = Instant.ofEpochMilli(fechaIngreso.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaActual = LocalDate.now();

            long weeks = ChronoUnit.WEEKS.between(fechaLote, fechaActual) + 1;
            long days = ChronoUnit.DAYS.between(fechaLote, fechaActual);

            int dia = (int) (days % 7);

            if (dia == 0) {
                loteEdadQueryDTO.setSemana((int) weeks - 1);
                loteEdadQueryDTO.setDia(7);
            } else {
                loteEdadQueryDTO.setSemana((int) weeks);
                loteEdadQueryDTO.setDia(dia);
            }
            return loteEdadQueryDTO;
        }
    }
}
