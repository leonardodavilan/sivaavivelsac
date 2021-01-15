package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.siva.models.dto.FiltroConsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.ControlRoedor;


import java.util.List;

public interface IControlRoedorDao extends JpaRepository<ControlRoedor, Integer>{

    @Query(" select c from ControlRoedor c " +
            "where (coalesce(:#{#filtroConsumoDTO.prdGranjaId}, 0) = 0 or c.prdGranja.id = :#{#filtroConsumoDTO.prdGranjaId}) " +
            "and (coalesce(:#{#filtroConsumoDTO.zonasubzonacontrolId}, 0) = 0 or c.zonaSubZonaControl.id = :#{#filtroConsumoDTO.zonasubzonacontrolId}) " +
            "and c.fecha between :#{#filtroConsumoDTO.fechaDesde} and :#{#filtroConsumoDTO.fechaHasta} " +
            "order by c.fecha asc ")
    List<ControlRoedor> findAllByFiltro(@Param("filtroConsumoDTO") FiltroConsumoDTO filtroConsumoDTO  );

}
