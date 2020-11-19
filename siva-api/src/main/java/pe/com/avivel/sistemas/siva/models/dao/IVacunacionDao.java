package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.siva.models.dto.FiltroVacunacionDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Vacunacion;

import java.util.List;

@Repository
public interface IVacunacionDao extends JpaRepository<Vacunacion, Integer>{


    @Query(" select v from Vacunacion v " +
            "where (coalesce(:#{#filtroVacunacionDTO.prdEtapa}, '') = '' or v.lote.loteEtapa = :#{#filtroVacunacionDTO.prdEtapa}) " +
            "and (coalesce(:#{#filtroVacunacionDTO.prdLoteId}, 0) = 0 or v.lote.id = :#{#filtroVacunacionDTO.prdLoteId}) " +
            "and v.fecha between :#{#filtroVacunacionDTO.fechaDesde} and :#{#filtroVacunacionDTO.fechaHasta} " +
            "order by v.fecha asc ")
    List<Vacunacion> findAllByFiltro(@Param("filtroVacunacionDTO") FiltroVacunacionDTO filtroVacunacionDTO  );


}

