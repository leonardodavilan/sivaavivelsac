package pe.com.avivel.sistemas.siva.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.com.avivel.sistemas.siva.models.dto.FiltroSolicitudDTO;
import pe.com.avivel.sistemas.siva.models.entity.vacunacion.SolicitudItem;

import java.util.List;

public interface ISolicitudItemDao extends JpaRepository<SolicitudItem,Integer> {


    @Query(" select s from SolicitudItem s " +
            "where s.solicitud.codigo = :#{#codigoSolicitud}")
    List<SolicitudItem> findAllByCodigoSi(@Param("codigoSolicitud") Integer codigoSolicitud);

    @Query(" select s from SolicitudItem s " +
            "where " +
            "s.solicitud.fecha between :#{#filtroSolicitudDTO.fechaDesde} and :#{#filtroSolicitudDTO.fechaHasta} " +
            "and  (coalesce(:#{#filtroSolicitudDTO.codigoSolicitud}, '') = '' or s.solicitud.codigo = :#{#filtroSolicitudDTO.codigoSolicitud})" +
            "order by s.solicitud.fecha asc ")
    List<SolicitudItem> findAllByFiltroSi(@Param("filtroSolicitudDTO") FiltroSolicitudDTO filtroSolicitudDTO);

}
