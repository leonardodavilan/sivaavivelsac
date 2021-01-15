package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.ZonaSubZonaControl;

import java.util.List;

public interface IZonaSubZonaControlDao extends JpaRepository<ZonaSubZonaControl, Integer>{


    @Query("select i from ZonaSubZonaControl i "+
            "where i.subZonaControl.id = :#{#subZonaControlId}")
    List<ZonaSubZonaControl> findAllBySubZonaControl(@Param("subZonaControlId") Integer subZonaControlId);

}
