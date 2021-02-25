package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.siva.models.dto.FiltroMovimientoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Movimiento;

import java.util.List;

@Repository
public interface IMovimientoDao extends JpaRepository<Movimiento, Integer>{

    @Query(" select m from Movimiento m " +
            "where (coalesce(:#{#filtroMovimientoDTO.prdGranjaId}, 0) = 0 or m.prdGranja.id = :#{#filtroMovimientoDTO.prdGranjaId}) " +
            "and (coalesce(:#{#filtroMovimientoDTO.tipoMovimientoId}, 0) = 0 or m.tipoMovimiento.id = :#{#filtroMovimientoDTO.tipoMovimientoId}) " +
            "and (coalesce(:#{#filtroMovimientoDTO.fechaDesde},0)=0 or coalesce(:#{#filtroMovimientoDTO.fechaHasta},0)=0 or m.fecha between :#{#filtroMovimientoDTO.fechaDesde} and :#{#filtroMovimientoDTO.fechaHasta} ) ")
    List<Movimiento> findAllByFiltro(@Param("filtroMovimientoDTO") FiltroMovimientoDTO filtroMovimientoDTO  );

}

