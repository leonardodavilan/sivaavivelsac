package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.siva.models.dto.FiltroConsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Consumo;

import java.util.List;


public interface IConsumoDao extends JpaRepository<Consumo, Integer>{


    @Query(" select c from Consumo c " +
            "where (coalesce(:#{#filtroConsumoDTO.prdGranjaId}, 0) = 0 or c.controlRoedor.prdGranja.id = :#{#filtroConsumoDTO.prdGranjaId}) " +
            "and (coalesce(:#{#filtroConsumoDTO.zonasubzonacontrolId}, 0) = 0 or c.controlRoedor.zonaSubZonaControl.id = :#{#filtroConsumoDTO.zonasubzonacontrolId}) " +
            "and c.controlRoedor.fecha between :#{#filtroConsumoDTO.fechaDesde} and :#{#filtroConsumoDTO.fechaHasta} " +
            "order by c.controlRoedor.fecha asc ")
    List<Consumo> findAllByFiltro(@Param("filtroConsumoDTO") FiltroConsumoDTO filtroConsumoDTO  );

}
