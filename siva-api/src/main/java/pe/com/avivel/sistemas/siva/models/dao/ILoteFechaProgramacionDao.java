package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.siva.models.dto.FiltroVacunacionDTO;
import pe.com.avivel.sistemas.siva.models.entity.produccion.LoteFechaProgramacion;

import java.math.BigDecimal;
import java.util.List;


public interface ILoteFechaProgramacionDao extends JpaRepository<LoteFechaProgramacion, BigDecimal>{


    @Query("select distinct lfp from LoteFechaProgramacion lfp " +
            "where (coalesce(:#{#filtroVacunacionDTO.prdLoteId}, 0) = 0 or lfp.lote_id = :#{#filtroVacunacionDTO.prdLoteId})  " +
            "and (coalesce(:#{#filtroVacunacionDTO.prdEtapa}, '') = '' or lfp.lote.loteEtapa = :#{#filtroVacunacionDTO.prdEtapa}) " )
    List<LoteFechaProgramacion> findAllByFiltroLote(@Param("filtroVacunacionDTO") FiltroVacunacionDTO filtroVacunacionDTO);

    @Query("select distinct lfp from LoteFechaProgramacion lfp " +
            "where (coalesce(:#{#filtroVacunacionDTO.prdLoteId}, 0) = 0 or lfp.lote_id = :#{#filtroVacunacionDTO.prdLoteId})  " +
            "and (coalesce(:#{#filtroVacunacionDTO.prdEtapa}, '') = '' or lfp.lote.loteEtapa = :#{#filtroVacunacionDTO.prdEtapa}) " +
            "and lfp.vacunacionFecha is not null")
    List<LoteFechaProgramacion> findAllByFiltroLoteOnlyFechVac(@Param("filtroVacunacionDTO") FiltroVacunacionDTO filtroVacunacionDTO);



    @Query("select distinct lfp from LoteFechaProgramacion lfp " +
            "where (coalesce(:#{#filtroVacunacionDTO.prdLoteId}, 0) = 0 or lfp.lote_id = :#{#filtroVacunacionDTO.prdLoteId}) " +
            "and (coalesce(:#{#filtroVacunacionDTO.prdEtapa}, '') = '' or lfp.lote.loteEtapa = :#{#filtroVacunacionDTO.prdEtapa}) " +
            "and lfp.vacunacionFecha between :#{#filtroVacunacionDTO.fechaDesde} and :#{#filtroVacunacionDTO.fechaHasta} " +
            "order by lfp.fechaProgramada desc " )
    List<LoteFechaProgramacion> findAllByFullFiltroFechaVac(@Param("filtroVacunacionDTO") FiltroVacunacionDTO filtroVacunacionDTO);


    @Query("select distinct lfp from LoteFechaProgramacion lfp " +
            "where (coalesce(:#{#filtroVacunacionDTO.prdLoteId}, 0) = 0 or lfp.lote_id = :#{#filtroVacunacionDTO.prdLoteId}) " +
            "and (coalesce(:#{#filtroVacunacionDTO.prdEtapa}, '') = '' or lfp.lote.loteEtapa = :#{#filtroVacunacionDTO.prdEtapa}) " +
            "and lfp.fechaProgramada between :#{#filtroVacunacionDTO.fechaDesde} and :#{#filtroVacunacionDTO.fechaHasta} " +
            "order by lfp.fechaProgramada desc " )
    List<LoteFechaProgramacion> findAllByFullFiltroFechaProg(@Param("filtroVacunacionDTO") FiltroVacunacionDTO filtroVacunacionDTO);


}
