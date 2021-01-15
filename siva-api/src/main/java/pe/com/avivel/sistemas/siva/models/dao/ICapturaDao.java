package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.siva.models.dto.FiltroConsumoDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Captura;


import java.util.List;

public interface ICapturaDao extends JpaRepository<Captura, Integer>{



    @Query(" select c from Captura c " +
            "where (coalesce(:#{#filtroConsumoDTO.prdGranjaId}, 0) = 0 or c.consumo.controlRoedores.prdGranja.id = :#{#filtroConsumoDTO.prdGranjaId}) " +
            "and (coalesce(:#{#filtroConsumoDTO.zonasubzonacontrolId}, 0) = 0 or c.consumo.controlRoedores.zonaSubZonaControl.id = :#{#filtroConsumoDTO.zonasubzonacontrolId}) " +
            "and c.consumo.controlRoedores.fecha between :#{#filtroConsumoDTO.fechaDesde} and :#{#filtroConsumoDTO.fechaHasta} " +
            "order by c.consumo.controlRoedores.fecha asc ")
    List<Captura> findAllByFiltro(@Param("filtroConsumoDTO") FiltroConsumoDTO filtroConsumoDTO  );


}
