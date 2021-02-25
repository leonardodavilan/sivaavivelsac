package pe.com.avivel.sistemas.siva.models.entity.produccion;

import lombok.Data;
import pe.com.avivel.sistemas.siva.models.dto.VacunaCalculadaQueryDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.LoteSerie;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;


@Entity
@Data
@Table(name = "vw_lote_fechaprogramacion")
public class LoteFechaProgramacion implements Serializable {

    @Id
    @Column(name="id_vista")
    private BigDecimal id;

    @ManyToOne(targetEntity = PrdLote.class)
    @JoinColumn(name = "lote")
    private PrdLote lote;

    @Column(name="lote_id")
    private Integer lote_id;

    @ManyToOne(targetEntity = ProgramacionVacuna.class)
    @JoinColumn(name = "programacion_vacuna_id")
    private ProgramacionVacuna programacionVacuna;

    @ManyToOne(targetEntity = NumeroProgramacion.class)
    @JoinColumn(name = "numero_programacion_id")
    private NumeroProgramacion numeroProgramacion;

    @Column(name="insumo_descripcion")
    private String insumoDescripcion;

    @ManyToOne(targetEntity = InsumoProveedor.class)
    @JoinColumn(name = "insumo_proveedor_id")
    private  InsumoProveedor insumoProveedor;

    @ManyToOne(targetEntity = Presentacion.class)
    @JoinColumn(name = "presentacion_id")
    private  Presentacion presentacion;

    @Column(name="granjaNombre")
    private String granjaNombre;

    @ManyToOne(targetEntity = PrdGranja.class)
    @JoinColumn(name = "granja_id")
    private PrdGranja granja;

    @Column(name="etapa")
    private String etapa;

    @Column(name="edad")
    private Integer edad;

    @Column(name="tiempo")
    private String tiempo;

    @Column(name="fecha_programada")
    @Temporal(value = TemporalType.DATE)
    private Date fechaProgramada;

    @Column(name="fecha_ingreso")
    @Temporal(value = TemporalType.DATE)
    private Date fechaIngreso;

    @Column(name="vacunacion_fecha")
    @Temporal(value = TemporalType.DATE)
    private Date vacunacionFecha;

    @ManyToOne(targetEntity = Vacunacion.class)
    @JoinColumn(name = "vacunacion_id")
    private Vacunacion vacunacion;

    @ManyToOne(targetEntity = MetodoVacuna.class)
    @JoinColumn(name = "metodo_vacuna_id")
    private MetodoVacuna metodoVacuna;

    @ManyToOne(targetEntity = Proveedor.class)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne(targetEntity = LoteSerie.class)
    @JoinColumn(name = "loteserie_id")
    private LoteSerie loteSerie;

    @Column(name="vacunacion_observacion")
    private String observacion;

    private static final long serialVersionUID = 1L;



    public VacunaCalculadaQueryDTO getCantidadPresentaciones() {

        VacunaCalculadaQueryDTO vacunaCalculadaQueryDTONew = new VacunaCalculadaQueryDTO();
        double poblacion = lote.getNumeroInicialAves();
        int cantVacunas;
        double residuo;

        List<Presentacion> arrayObjetos = programacionVacuna.getInsumo().getItemsPresentaciones();

        Collections.sort(arrayObjetos, new Comparator<Presentacion>() {
            @Override
            public int compare(Presentacion p1, Presentacion p2) {
                return new BigDecimal(String.valueOf(p2.getCantidad())).compareTo(new BigDecimal(String.valueOf(p1.getCantidad())));
            }
        });
        ArrayList<VacunaCalculadaQueryDTO> arrayObjetosNew = new ArrayList<>();
        int diferencia = 0;

        for (int i=0;i<arrayObjetos.size();i++){

            String presentacion_ = arrayObjetos.get(i).getNombre();
            int intIndex = presentacion_.indexOf("ML");
            if(intIndex != -1){
                return vacunaCalculadaQueryDTONew;
            }

            int cantidadPresentacion =  arrayObjetos.get(i).getCantidad().intValue();
            cantVacunas = (int) poblacion / cantidadPresentacion;
            residuo = poblacion % cantidadPresentacion;

            if(residuo == 0 ){
                arrayObjetosNew.add(new VacunaCalculadaQueryDTO(arrayObjetos.get(i),BigDecimal.valueOf(cantVacunas),0));
                break;
            }
            else{
                if(i == arrayObjetos.size()-1){
                    cantVacunas = cantVacunas + 1;
                    diferencia =  cantidadPresentacion*cantVacunas - (int)poblacion;
                    arrayObjetosNew.add(new VacunaCalculadaQueryDTO(arrayObjetos.get(i),BigDecimal.valueOf(cantVacunas),diferencia));
                    break;
                }else{
                    arrayObjetosNew.add(new VacunaCalculadaQueryDTO(arrayObjetos.get(i),BigDecimal.valueOf(cantVacunas),0));
                    poblacion = residuo;
                }
            }

        }

        for (int i=0;i<arrayObjetosNew.size();i++){
         if(presentacion.getId() == arrayObjetos.get(i).getId().intValue()){
             return arrayObjetosNew.get(i);
         }
        }
        return vacunaCalculadaQueryDTONew;
    }
    public BigDecimal getTotal(){
        if(getCantidadPresentaciones().getCantidad() != null && insumoProveedor.getPrecio() != null){
            return getCantidadPresentaciones().getCantidad().multiply(insumoProveedor.getPrecio());
        }else{
            return null;
        }
    }
    public BigDecimal getPrecio(){
        if(insumoProveedor.getPrecio() != null){
            return insumoProveedor.getPrecio();
        }
        return null;
    }
    public Integer getCantidadCalculada(){
        if(getCantidadPresentaciones().getCantidad() != null){
            return getCantidadPresentaciones().getCantidad().intValue();
        }
        return 0;
    }
    public Integer getPoblacion(){
        if(lote.getNumeroInicialAves() != null){
            return lote.getNumeroInicialAves();
        }
        return null;
    }
    public String getLoteCodigo(){
        if(lote.getCodigo() != null){
            return lote.getCodigo();
        }
        return null;
    }
}