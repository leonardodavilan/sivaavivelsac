package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.avivel.sistemas.siva.models.dto.FiltroSolicitudDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.Solicitud;

import java.util.List;

@Repository
public interface ISolicitudDao extends JpaRepository<Solicitud, Integer>{


    @Query(" select s from Solicitud s " +
            "where " +
            "s.fecha between :#{#filtroSolicitudDTO.fechaDesde} and :#{#filtroSolicitudDTO.fechaHasta} " +
            "and  (coalesce(:#{#filtroSolicitudDTO.codigoSolicitud}, 0) = 0 or s.codigo = :#{#filtroSolicitudDTO.codigoSolicitud})" +
            "order by s.fecha asc ")
    List<Solicitud> findAllByFiltro(@Param("filtroSolicitudDTO") FiltroSolicitudDTO filtroSolicitudDTO);

    @Query(" select s from Solicitud s " +
            "where s.codigo = :#{#codigoSolicitud}")
    List<Solicitud> findAllByCodigo(@Param("codigoSolicitud") Integer codigoSolicitud);

    @Query(" select max(s.codigo) as maxcodigo from Solicitud s ")
    Integer findMaxCodSolicitud();
}

